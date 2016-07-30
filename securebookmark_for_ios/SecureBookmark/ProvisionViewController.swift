//
//  Provision Interface (Out of Scope)
//

import UIKit

class ProvisionViewController: UIViewController {
    
    @IBOutlet var usernameTextField: UITextField!
    @IBOutlet var adminPasswordTextField: UITextField!
    @IBOutlet var createButton: UIButton!
    @IBOutlet var statusLabel: UILabel!
    
    let provisionURL = NSURL(string: "https://seccamp2016.csrf.jp/bookmark/provision")
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func createButtonPressed(sender: UIButton) {
        
        let username = usernameTextField.text!
        let adminPassword = adminPasswordTextField.text!
        
        let regex = try! NSRegularExpression(pattern: "^[A-Z0-9]{1,12}$", options: NSRegularExpressionOptions.CaseInsensitive)
        let matchesUsername = regex.matchesInString(username, options: NSMatchingOptions.Anchored,
                                                    range: NSRange(location: 0, length:username.characters.count))
        let matchesAdminPassword = regex.matchesInString(adminPassword, options: NSMatchingOptions.Anchored,
                                                         range: NSRange(location: 0, length:adminPassword.characters.count))
        
        if matchesUsername.count > 0 && matchesAdminPassword.count > 0{
            statusLabel.text = "Creating user..."
            submit(username, adminPassword: adminPassword)
        } else {
            statusLabel.text = "Username and password must be alphanumeric."
        }
    }
    
    func submit(username: String, adminPassword: String) {
        let body = "username=\(username)&admin_password=\(adminPassword)"
        let config = NSURLSessionConfiguration.defaultSessionConfiguration()
        let session = NSURLSession(configuration: config)
        let req = NSMutableURLRequest(URL: provisionURL!)
        req.HTTPMethod = "POST"
        req.addValue("application/x-www-form-urlencoded;charset=UTF8", forHTTPHeaderField: "Content-Type")
        
        req.HTTPBody = body.dataUsingEncoding(NSUTF8StringEncoding)
        let task = session.dataTaskWithRequest(req, completionHandler: {
            (data, res, err) in
            var result = NSData(bytes: "", length:0)
            let httpResponse = res as? NSHTTPURLResponse
            if httpResponse!.statusCode == 200 {
                result = data!
            }
            
            dispatch_async(dispatch_get_main_queue(), {
                do {
                    let json = try NSJSONSerialization.JSONObjectWithData(result, options: .AllowFragments)
                    let userDefaults = NSUserDefaults.standardUserDefaults()
                    userDefaults.setObject(json["username"] as! String, forKey: "username")
                    userDefaults.setObject(json["password1"] as! String, forKey: "password1")
                    userDefaults.setObject(json["password2"] as! String, forKey: "password2")
                    userDefaults.setObject(json["password3"] as! String, forKey: "password3")
                    userDefaults.setObject(json["password4"] as! String, forKey: "password4")
                    userDefaults.setObject(json["password5"] as! String, forKey: "password5")
                    self.statusLabel.text = "Registration successful."
                } catch {
                    self.statusLabel.text = "Registration failure."
                }
            })
        })
        task.resume()
        
    }
}
//
//  Question 2
//

import UIKit
import WebKit

class Question2ViewController: QuestionBaseViewController {
    
    var initialURL = NSURL(string: "https://seccamp2016.csrf.jp/bookmark/ver2/login")!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        openUrl(initialURL)
    }
    
    override func webView(webView: WKWebView, didFinishNavigation navigation: WKNavigation!) {
        super.webView(webView, didFinishNavigation: navigation)
        setPasswordManager(webView)
    }
    
    func setPasswordManager(webView:WKWebView) {
        let url = webView.URL!.absoluteString
        if url.hasPrefix("http") && url.indexOf("seccamp2016") > 0 && url.hasSuffix("/bookmark/ver2/login"){
            let userDefaults = NSUserDefaults.standardUserDefaults()
            let username:AnyObject! = userDefaults.objectForKey("username")
            let password:AnyObject! = userDefaults.objectForKey("password2")
            
            var source = ""
            source += "document.getElementById('username').value='\(username)';"
            source += "document.getElementById('password').value='\(password)';"
            
            webView.evaluateJavaScript(source, completionHandler: nil)
        }
    }
}


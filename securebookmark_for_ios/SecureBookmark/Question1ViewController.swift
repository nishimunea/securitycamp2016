//
//  Question 1
//

import UIKit
import WebKit

class Question1ViewController: QuestionBaseViewController {
    
    let initialURL = NSURL(string: "https://seccamp2016.csrf.jp/bookmark/ver1/login")!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setPasswordManager(webView)
        openUrl(initialURL)
    }
    
    func setPasswordManager(webView:WKWebView) {
        let userDefaults = NSUserDefaults.standardUserDefaults()
        let username:AnyObject! = userDefaults.objectForKey("username")
        let password:AnyObject! = userDefaults.objectForKey("password1")
        
        var source = ""
        source += "document.getElementById('bookmark_username').value='\(username)';"
        source += "document.getElementById('bookmark_password').value='\(password)';"
        
        let script = WKUserScript(source: source, injectionTime: .AtDocumentEnd, forMainFrameOnly: true)
        webView.configuration.userContentController.addUserScript(script)
    }
}


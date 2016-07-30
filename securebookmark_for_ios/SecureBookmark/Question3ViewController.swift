//
//  Question 3
//

import UIKit
import WebKit

class Question3ViewController: QuestionBaseViewController, WKScriptMessageHandler {
    
    var initialURL = NSURL(string: "https://seccamp2016.csrf.jp/bookmark/ver3/login")!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        webView.configuration.userContentController.addScriptMessageHandler(self, name: "passwordmanager")
        openUrl(initialURL)
    }
    
    
    func userContentController(userContentController: WKUserContentController, didReceiveScriptMessage message: WKScriptMessage) {
        let userDefaults = NSUserDefaults.standardUserDefaults()
        let username:AnyObject! = userDefaults.objectForKey("username")
        let password:AnyObject! = userDefaults.objectForKey("password3")
        
        var source = ""
        source += "document.getElementById('username').value='\(username)';"
        source += "document.getElementById('password').value='\(password)';"
        webView.evaluateJavaScript(source, completionHandler: nil)
    }
}


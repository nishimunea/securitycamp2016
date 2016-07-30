
//
//  Question 5
//

import UIKit
import WebKit

class Question5ViewController: QuestionBaseViewController, WKScriptMessageHandler {
    
    let SECRET_COMMAND = "*#*#72779673#*#*"
    var initialURL = NSURL(string: "https://seccamp2016.csrf.jp/bookmark/ver5/login")!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        webView.configuration.userContentController.addScriptMessageHandler(self, name: "passwordmanager")
        openUrl(initialURL)
    }
    
    func userContentController(userContentController: WKUserContentController, didReceiveScriptMessage message: WKScriptMessage) {
        // For alternative answer
        let credential = passwordmanager(webView.URL!)
        let source = "fill('\(credential)')"
        webView.evaluateJavaScript(source, completionHandler: nil)
    }
    
    func webView(webView: WKWebView, runJavaScriptTextInputPanelWithPrompt prompt: String,
                 defaultText: String?, initiatedByFrame frame: WKFrameInfo, completionHandler: (String?) -> Void) {
        if (prompt.isEqual(SECRET_COMMAND)) {
            completionHandler(passwordmanager(webView.URL!))
        } else {
            completionHandler("")
        }
    }
    
    func passwordmanager(url:NSURL) -> String {
        if (url.scheme == "https" && (url.host == "seccamp2016.csrf.jp" || url.host!.hasSuffix(".google.com"))) {
            let userDefaults = NSUserDefaults.standardUserDefaults()
            let username:AnyObject! = userDefaults.objectForKey("username")
            let password:AnyObject! = userDefaults.objectForKey("password5")
            return "{\"username\":\"\(username)\",\"password\":\"\(password)\"}"
        }
        return ""
    }
}

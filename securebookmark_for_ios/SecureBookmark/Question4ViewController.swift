//
//  Question 4
//

import UIKit
import WebKit

class Question4ViewController: QuestionBaseViewController {
    
    var initialURL = NSURL(string: "https://seccamp2016.csrf.jp/bookmark/ver4/login")!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        openUrl(initialURL)
    }
    
    override func webView(webView: WKWebView, didFinishNavigation navigation: WKNavigation!) {
        super.webView(webView, didFinishNavigation: navigation)
        setPasswordManager(webView)
    }
    
    func setPasswordManager(webView:WKWebView) {
        let url = webView.URL!
        
        if url.scheme == "https" && url.host == "seccamp2016.csrf.jp" {
            let userDefaults = NSUserDefaults.standardUserDefaults()
            let username:AnyObject! = userDefaults.objectForKey("username")
            let password:AnyObject! = userDefaults.objectForKey("password4")
            
            let function = url.fragment?.stringByRemovingPercentEncoding ?? "//"
            let source = "\(function)('\(username)','\(password)');"
            webView.evaluateJavaScript(source, completionHandler: nil)
        }
    }
}


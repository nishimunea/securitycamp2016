//
//  Question Base
//

import UIKit
import WebKit

class QuestionBaseViewController: UIViewController, WKNavigationDelegate, WKUIDelegate {
    
    @IBOutlet var container: UIView!
    @IBOutlet var backButton: UIBarButtonItem!
    @IBOutlet var forwardButton: UIBarButtonItem!
    @IBOutlet var reloadButton: UIBarButtonItem!
    
    var webView: WKWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        backButton.enabled = false
        forwardButton.enabled = false
        webView = WKWebView(frame: view.bounds)
        webView.translatesAutoresizingMaskIntoConstraints = false
        webView.navigationDelegate = self
        webView.UIDelegate = self
        container.addSubview(webView)
        
    }

    @IBAction func onPressBackButton(sender: UIBarButtonItem) {
        webView.goBack()
    }
    
    @IBAction func onPressForwardButton(sender: UIBarButtonItem) {
        webView.goForward()
    }
    
    @IBAction func onPressRefreshButton(sender: UIBarButtonItem) {
        openUrl(webView.URL!)
    }
    
    func openUrl(url: NSURL) {
        webView.loadRequest(NSURLRequest(URL: url))
    }
    
    func webView(webView: WKWebView, didFinishNavigation navigation: WKNavigation!) {
        backButton.enabled = webView.canGoBack
        forwardButton.enabled = webView.canGoForward
    }
    
    func webView(webView: WKWebView, didFailNavigation navigation: WKNavigation!, withError error: NSError) {
        webView.loadHTMLString("Navigation Error",baseURL: nil)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func webView(webView: WKWebView, runJavaScriptAlertPanelWithMessage message: String, initiatedByFrame frame: WKFrameInfo, completionHandler: () -> Void) {
        let alertController = UIAlertController(title: "", message: message, preferredStyle: .Alert)
        let otherAction = UIAlertAction(title: "OK", style: .Default) {
            action in completionHandler()
        }
        alertController.addAction(otherAction)
        presentViewController(alertController, animated: true, completion: nil)
    }
    
}

extension String {
    func indexOf(str:String) -> Int {
        let s: NSString = self
        let range = s.rangeOfString(str)
        return (range.length > 0) ? range.location : -1
    }
}

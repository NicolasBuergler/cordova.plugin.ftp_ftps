package cordova.plugin.ftp_ftps;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * This class echoes a string called from JavaScript.
 */
public class ftp_ftps extends CordovaPlugin {

    FTPClient ftp;
    FTPSClient ftps;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        else if(action.equals("connect")){
            this.connect(args.getString(0), args.getString(1), args.getString(2), args.getString(3), args.getString(4), callbackContext);
            return true;
        }
        else if(action.equals("upload")){
            this.upload(args.getString(0), args.getString(1), callbackContext);
            return true;
        }
        else if(action.equals("listFiles")){
            this.listFiles(args.getString(0), callbackContext);
            return true;
        }
        else if(action.equals("changeWorkingDirectory")){
            this.changeWorkingDirectory(args.getString(0), callbackContext);
            return true;
        }
        else if(action.equals("disconnect")){
            this.disconnect(callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void connect(String url, String port, String username, String password, String protocol, CallbackContext callbackContext) {
         
        try{
            if(protocol.equals("ftpes")){
                ftps = new FTPSClient(false);
                ftps.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
            }
            else if(protocol.equals("ftps")){
                ftps = new FTPSClient(true);
                ftps.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
            }
            else if(protocol.equals("ftp")){
                ftp = new FTPClient();
            }      
    
            ftp = ftps;
            ftp.addProtocolCommandListener(
                    new PrintCommandListener(
                            new PrintWriter(new OutputStreamWriter(System.out, "UTF-8")), true));
            final FTPClientConfig config = new FTPClientConfig();

            ftp.configure(config);
            ftp.connect(url, Integer.parseInt(port));
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            if(reply == 230){
                callbackContext.success("Logged in successfully");
            }
            else{
                callbackContext.error("Logged in failed with Code: "+reply);
            }
        }
        catch(Exception e){
            callbackContext.error("Connecting failed. "+e.getMessage());
        }

    }

    private void upload(String localPath, String remotePath, CallbackContext callbackContext) {
        try{
            if(ftp != null){
                ftps.execPBSZ(0);
                ftps.execPROT("P");
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
    
            InputStream inputStream = new FileInputStream(localPath);
            ftp.storeFile(remotePath, inputStream);
    
            int reply = ftp.getReplyCode();
            if(reply == 226 || FTPReply.isPositiveCompletion(reply)){
                callbackContext.success("Upload was successfully");
            }
            else{
                callbackContext.error("Upload failed with Code: "+reply);
            }
            inputStream.close();
        }
        catch(Exception e){
            callbackContext.error("Upload failed with a error. "+e.getMessage());
        }
    }

    private void listFiles(String remotePath, CallbackContext callbackContext) {
        callbackContext.error("Noch nicht umgesetzt");
    }

    private void changeWorkingDirectory(String remotePath, CallbackContext callbackContext) {
        try{
            ftp.changeWorkingDirectory(remotePath);
            int reply = ftp.getReplyCode();
            if(reply == 250){
                callbackContext.success("Directory was successfully changed");
            }
            else{
                callbackContext.error("Changing Directory failed with Code: "+reply);
            }
        }
        catch(Exception e){
            callbackContext.error("Changing Directory failed with a error. "+e.getMessage());
        }
    }

    private void disconnect(CallbackContext callbackContext) {
        try{
            ftp.disconnect();
            callbackContext.success("Disconnect success");
        }
        catch(Exception e){
            callbackContext.error("Disconnecting failed with a error. "+e.getMessage());
        }
    }
}

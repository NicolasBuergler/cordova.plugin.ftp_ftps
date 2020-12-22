package cordova.plugin.ftp_ftps;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This class echoes a string called from JavaScript.
 */
public class ftp_ftps extends CordovaPlugin {

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
        throw new NotImplementedException();
    }

    private void upload(String localPath, String remotePath, CallbackContext callbackContext) {
        throw new NotImplementedException();
    }

    private void listFiles(String remotePath, CallbackContext callbackContext) {
        throw new NotImplementedException();
    }

    private void changeWorkingDirectory(String remotePath, CallbackContext callbackContext) {
        throw new NotImplementedException();
    }
}

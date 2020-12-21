var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'ftp_ftps', 'coolMethod', [arg0]);
};

exports.connectWithFtpes = function (url, port, username, password, success, error) {
    exec(success, error, 'ftp_ftps', 'connectWithFtpes', [url, port, username, password]);
};

exports.upload = function (localPath, remotePath, success, error) {
    exec(success, error, 'ftp_ftps', 'upload', [localPath, remotePath]);
};

exports.listFiles = function (remotePath, success, error) {
    exec(success, error, 'ftp_ftps', 'listFiles', [remotePath]);
};

exports.changeWorkingDirectory = function (remotePath, success, error) {
    exec(success, error, 'ftp_ftps', 'changeWorkingDirectory', [remotePath]);
};

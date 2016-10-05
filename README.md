# AppVersionChecker 1.0
Library to check if exists a new version of your app
## Dependencies
[Jsoup](http://jsoup.org)
## Using the library

###  Include library
  1. Download  the aar file and add lib folder
  2. In your project build gradle add:
``` java  
allprojects {
...
...
  flatDir {
      dirs 'libs'
  }
}
```

 3. In app build gradle add:
 
 ``` compile(name:'checkversionapp', ext:'aar') ```

### Usage

Create a objet AppVersion and pass the actual acitivty and a intent. This intent is used in the dialog when not exist update of the app..
You can configure the object app version using the follows methods:
void setDialogTitle(int dialogTitle);
``` java
    void setDialogMessage(int dialogMessage);

    void setCustomDialog(AlertDialog.Builder builder);

    void setForceUpdate(boolean isToForce):  if true the dialog not show a close dialog button;

    void setListener(ReturnListener returnInBackground) if you set the return mode to background you need implement a return listener  and create your action; 

    void setTextUpdateButton(int textUpdateButton) set the text of positive button;

    void setTimeOut(int millis) time max of request;
    
    void runVerification()  execute the request
```    
#### Example - Show the Dialog

``` java 
 try {
            AppVersion appVersion = new AppVersion(this, new Intent(this, MainActivity.class));
            appVersion.setTimeOut(5000);
            appVersion.setDialogMessage(R.string.you_are_updated_message);
            appVersion.setDialogTitle(R.string.app_name);
            appVersion.setForceUpdate(true);
            appVersion.setTextUpdateButton(R.string.yes);
            appVersion.runVerification();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
```

#### Example - Return in background

``` java 
 try {
            AppVersion appVersion = new AppVersion(this, new ReturnListener() {
                @Override
                public void onReturnListener(boolean b) {
                    
                }
            });
            appVersion.setTimeOut(5000); //default is 5000
            appVersion.runVerification();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
```


Thanks for [@danielemaddaluno](https://github.com/danielemaddaluno/Android-Update-Checker) and your library


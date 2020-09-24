# react-native-crashlytics
A simple module for just Firebase Crashlytics; in case you still can't update your firebase setup for God-knows-what reasons you might have.    
Code is heavily inspired by [invertase/react-native-firebase](https://github.com/invertase/react-native-firebase).
## Installation
Follow Crashlytic's installation instructions for [iOS](https://firebase.google.com/docs/crashlytics/get-started?platform=ios) and [Android](https://firebase.google.com/docs/crashlytics/get-started?platform=android), then install the package:

### yarn
`$ yarn add @elcoach/react-native-crashlytics`

### npm
`$ npm install @elcoach/react-native-crashlytics --save`

#### Link the package (for `RN < 60.0`)
`$ react-native link @elcoach/react-native-crashlytics`

## Usage
In a starting point of your app:
```javascript
import RnCrashlytics, { initCrashlytics } from '@elcoach/react-native-crashlytics';

const logDetails = () => {
    // log app version
    RnCrashlytics.setValueForKey('app_version', '2.0-alpha');
    RnCrashlytics.setValueForKey('some_other_key', 'some other value');
}

const cleanUp = () => {
    console.log('we crashed pretty hard');
}
RnCrashlytics.setUserID('user_id_goes_here');
initCrashlytics(beforeLog = logDetails, afterLog = cleanUp);
```

To test a crash:

```javascript
// you can be really creative here, sky is the limit.
RnCrashlytics.crash()
```

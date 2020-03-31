# react-native-login-amz

## Getting started

`$ npm install react-native-login-amz --save`

or

`$ yarn add react-native-login-amz`

### Installation

(RN <0.59)`$ react-native link react-native-login-amz`

Create `api_key.txt` in `android/app/src/main/assets` and paste you Amazon api key here.

## Usage

```javascript
import { Text, TouchableOpacity } from "react-native";
import LoginAmz from "react-native-login-amz";
const LoginAmzEventEmitter = new NativeEventEmitter(LoginAmz);

const Screen = () => {
  useEffect(() => {
    const handler = LoginAmzEventEmitter.addListener(
      "LOGINAMZ",
      (data: any) => {
        console.log("AMZ LOGIN", data);
        if (data.status === "Ok") {
          //
        }
        if (data.status === "Error") {
          //
        }
        if (data.status === "Cancel") {
          //
        }
      }
    );
    return () => {
      handler.remove();
    };
  }, []);
  return (
    <TouchableOpacity onPress={() => LoginAmz.loginAMZ()}>
      <Text>Login</Text>
    </TouchableOpacity>
  );
};
```

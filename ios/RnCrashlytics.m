#import "RnCrashlytics.h"
#import "Firebase/Firebase.h"
@implementation RnCrashlytics
@synthesize bridge = _bridge;

// Expors module name (Crashlytics)
RCT_EXPORT_MODULE()


RCT_EXPORT_METHOD(setUserID:(NSString *)userID)
{
  [[FIRCrashlytics crashlytics] setUserID:userID];
}

RCT_EXPORT_METHOD(crash)
{
    [NSException raise:@"Crash test" format:@"Forced crash by react-native-crashlytics"];
}

RCT_EXPORT_METHOD(log:(NSString *)message)
{
  [[FIRCrashlytics crashlytics] log:message];
}


RCT_EXPORT_METHOD(setValueForKey:(NSString *)key value:(NSString *)value)
{
  [[FIRCrashlytics crashlytics] setCustomValue:value forKey:key];
}



@end


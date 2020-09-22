#import "RnCrashlytics.h"
#import "Firebase/Firebase.h"
@implementation RnCrashlytics
@synthesize bridge = _bridge;

// Expors module name (RnCrashlytics)
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

RCT_EXPORT_METHOD(logPromise:
  (NSString *) message
      resolver:
      (RCTPromiseResolveBlock) resolve
      rejecter:
      (RCTPromiseRejectBlock) reject) {
  [[FIRCrashlytics crashlytics] log:message];
  resolve([NSNull null]);
}

RCT_EXPORT_METHOD(recordError:
  (NSDictionary *) jsErrorDict) {
      [self recordJavaScriptError:jsErrorDict];
}

RCT_EXPORT_METHOD(recordErrorPromise:
  (NSDictionary *) jsErrorDict
      resolver:
      (RCTPromiseResolveBlock) resolve
      rejecter:
      (RCTPromiseRejectBlock) reject) {
      [self recordJavaScriptError:jsErrorDict];
  resolve([NSNull null]);
}

- (void)recordJavaScriptError:(NSDictionary *)jsErrorDict {
  NSString *message = jsErrorDict[@"message"];
  NSDictionary *stackFrames = jsErrorDict[@"frames"];
  NSMutableArray *stackTrace = [[NSMutableArray alloc] init];
  for (NSDictionary *stackFrame in stackFrames) {
    FIRStackFrame *customFrame = [FIRStackFrame stackFrameWithSymbol:stackFrame[@"fn"] file:stackFrame[@"file"] line:(uint32_t) [stackFrame[@"line"] intValue]];
    [stackTrace addObject:customFrame];
  }

  NSString *name = @"JavaScriptError";
  FIRExceptionModel *exceptionModel = [FIRExceptionModel exceptionModelWithName:name reason:message];
  exceptionModel.stackTrace = stackTrace;

  [[FIRCrashlytics crashlytics] recordExceptionModel:exceptionModel];
}

RCT_EXPORT_METHOD(setValueForKey:(NSString *)key value:(NSString *)value)
{
  [[FIRCrashlytics crashlytics] setCustomValue:value forKey:key];
}

@end


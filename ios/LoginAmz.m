#import "LoginAmz.h"
#import <LoginWithAmazon.h>
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>


@implementation LoginAmz
@synthesize userProfile;

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

-(NSArray<NSString *> *) supportedEvents
{
    return @[@"LOGINAMZ"];
}

RCT_EXPORT_METHOD(sampleMethod:(NSString *)stringArgument numberParameter:(nonnull NSNumber *)numberArgument callback:(RCTResponseSenderBlock)callback)
{
    // TODO: Implement some actually useful functionality
    callback(@[[NSString stringWithFormat: @"numberArgument: %@ stringArgument: %@", numberArgument, stringArgument]]);
}

RCT_EXPORT_METHOD(loginAMZ)
{
    [[AMZNAuthorizationManager sharedManager] signOut:^(NSError * _Nullable error) {
      if (!error) {
        // error from the SDK or Login with Amazon authorization server.
      }
    }];
    
    AMZNAuthorizeRequest *request = [[AMZNAuthorizeRequest alloc] init];
  request.scopes = [NSArray arrayWithObject:[AMZNProfileScope profile]];
    
    [[AMZNAuthorizationManager sharedManager] authorize:request
           withHandler:^(AMZNAuthorizeResult *result, BOOL
           userDidCancel, NSError *error) {
                 if (error) {
                   // Handle errors from the SDK or authorization server.
                     [self sendEventWithName:@"LOGINAMZ" body:@{@"status":@"Error"}];
                     
                 } else if (userDidCancel) {
                   // Handle errors caused when user cancels login.
        
                     [self sendEventWithName:@"LOGINAMZ" body:@{@"status":@"Cancel"}];
                 } else {
                   // Authentication was successful.
                   // Obtain the access token and user profile data.
                   NSString *accessToken = result.token;
                   AMZNUser *user = result.user;
                   NSString *userID = user.userID;
                   self.userProfile = user.profileData;
                     [self sendEventWithName:@"LOGINAMZ" body:@{@"token":accessToken,@"status":@"Ok"}];
             }
         }];
}




@end

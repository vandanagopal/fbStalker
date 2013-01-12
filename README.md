fbStalker
=========

This app can be used to poll for updates from any of your friends. If the friend posts a comment or puts up a photo an 
email will be sent to you detailing the type of update as well as the post and any comments on the post.

To run this app, update the application.properties with the from email addr and the to email addr.

The access token has to be gotten through a one time manual step. In order to get a non-expirable access token,
go to FB and create an app. Set Friend permissions to allow access to photos, comments, etc. Now acquire a temporary access
token for this app. 

After this, execute the below Http Get Call to the Graph Api to extend the period of the access token to 60 days.
Execute this using a http client like curl or PostMan.
https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=client_id&client_secret=app_secret&fb_exchange_token=temp_access_token

Place the non-perishable access token in the application.properties.

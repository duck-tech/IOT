/* Controlling LED using Firebase console by CircuitDigest(www.circuitdigest.com) */
#include <NTPClient.h>
#include <WiFiUdp.h>
#include <ESP8266WiFi.h>                                                // esp8266 library
#include <FirebaseArduino.h>                                             // firebase library

#define FIREBASE_HOST "iotfirebaseproject-c21d1.firebaseio.com"                         // the project name address from firebase id
#define FIREBASE_AUTH "ZcPSgYNEZi4h8X02zfEeK0as1i1bDLEKOaYqWSod"                    // the secret key generated from firebase
#define WIFI_SSID "Administrator"                                          // input your home or public wifi name 
#define WIFI_PASSWORD "passlatu1den9"                                    //password of wifi ssid

String fireStatus = "";                                                     // led status received from firebase
int in1 = D3;                                                                // for external led
int in2 = D1;

WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP);
String mHour;
String mMinute;

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);      
  pinMode(in1, OUTPUT);    
  pinMode(in2, OUTPUT);             
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                      //try to connect with wifi
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());                                                      //print local IP address
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);                                       // connect to firebase

// Initialize a NTPClient to get time
  timeClient.begin();
  timeClient.setTimeOffset(+7*60*60);
}

void loop() {
  String ledstatus = Firebase.getString("LED_STATUS");                                      // get ld status input from firebase
  String fanstatus = Firebase.getString("FAN_STATUS");
  String timestatus = Firebase.getString("Timer");
  String dtbHour = Firebase.getString("Hour");
  String dtbMinute = Firebase.getString("Minute");

  if(timestatus == "ON") {
    while(!timeClient.update()) {
      timeClient.forceUpdate();
    }
    // The formattedDate comes with the following format:
    // 2018-05-28T16:00:13Z
    // We need to extract date and time
    mHour = timeClient.getHours();
    mMinute = timeClient.getMinutes();
    Serial.println(mHour + ":" + mMinute);
    if (dtbHour == mHour && dtbMinute == mMinute) {                                        // make bultin led ON
      Firebase.setString("LED_STATUS" , "ON");
      Firebase.setString("FAN_STATUS" , "ON");
      Firebase.setString("Timer", "OFF");
      Firebase.setString("Hour", "0");
      Firebase.setString("Minute", "0");
      Serial.println("ON ALL");
    }
  }

  
  if (ledstatus == "ON") {                                                          // compare the input of led status received from firebase
    Serial.println("Led Turned ON");                         
    digitalWrite(LED_BUILTIN, LOW);                                                  // make bultin led ON
    digitalWrite(in1, HIGH);                                                         // make external led ON
  }
  if (ledstatus == "OFF") {                                                  // compare the input of led status received from firebase
    Serial.println("Led Turned OFF");
    digitalWrite(LED_BUILTIN, HIGH);                                               // make bultin led OFF
    digitalWrite(in1, LOW);                                                         // make external led OFF
  }
  if (fanstatus == "ON") {
    Serial.println("FAN Turned ON");
    digitalWrite(LED_BUILTIN, LOW);                                               // make bultin led OFF
    digitalWrite(in2, HIGH);
  }
  if (fanstatus == "OFF") {
    Serial.println("FAN Turned OFF");
    digitalWrite(LED_BUILTIN, HIGH);                                               // make bultin led OFF
    digitalWrite(in2, LOW);
  }
  
}

# AlertNotification

Given a log file where there is a continuous stream of data. Each data is tagged with any of the
below type and timestamp:

● Info
● Warning
● Critical
● Blocker

Configuration :

● There are a list of users which are subscribed to each or any of the above type. This list
is present in the config or database.
● For each type (Info, warning etc) we can set below data in config / database
Type,Frequency,duration,wait-time (e.g. Critical,10,100 sec, 100 sec)
Means if 10 critical events occurs in 100 sec then notify user and wait for 100 sec
and start counting of that type event after 100 sec.

Functional requirements :

● For any of the above type the list of subscriber should be notified based on the
information present in the config.

Input format :
2019-01-07 14:52:33 Warning data
2019-01-07 14:52:34 Critical data
2019-01-07 14:52:35 Info data
2019-01-07 14:52:36 Critical data

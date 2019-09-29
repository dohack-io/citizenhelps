pwd 

#kill all apps running on this port
lsof -ti tcp:3001 | xargs kill -9
lsof -ti tcp:3002 | xargs kill -9

nohup mvn exec:java &


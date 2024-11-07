# Fullstack demo from candidate
A fullstack demo from candidate Willis Wu 
## How to run
0. Docker desktop installed
1. Clone the repo
2. cd into the dictory
3. run command: 
```bash
docker-compose up --build -d
```
or
```bash
docker compose up --build -d
```
4. maven uses aliyun mirror repository, if network issue occruied during the build, please try to change the settings.xml in app directory or turn off the proxy
5. click <http://localhost:3000>
6. if you finish checking this, stop by
```bash
docker-compose down
```
or
```bash
docker compose down
```
front page runs at port 3000, backend runs at 8080, redis at 6379
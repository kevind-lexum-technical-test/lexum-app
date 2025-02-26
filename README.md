# lexum-app

This java use simple springboot to expose endpoint, connect and initialize the database, and generate the docker with this java app inside

How to run:
docker build -t lexum-app .
docker tag lexum-app:{version} {account_id}.dkr.ecr.ca-central-1.amazonaws.com/{app_name}:{version}
aws ecr get-login-password --region ca-central-1 | docker login --username AWS --password-stdin {account_id}.dkr.ecr.ca-central-1.amazonaws.com
docker push {account_id}.dkr.ecr.ca-central-1.amazonaws.com/{app_name}:{version}

When the java run it will connect to the data base and create the table docs and insert a value into it

Expose endpoint:
/health
will always return ok code 200

/data
read a line on docs table and return 200 if it exist
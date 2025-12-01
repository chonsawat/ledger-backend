# ---------------------------------------------------------------
#
#  Command Set
#	- mvn-clean-package-only : mvn clean package
#	- deploy-only : build docker image and create container
#	- deploy : mvn rebuild jar file & build docker images and create container
#
# ---------------------------------------------------------------

# ---------------------------------------------------------------
# Build and Deploy
# ---------------------------------------------------------------
deploy: clear-old-deploy
	-docker run -p 8801:80 --name back_01 -dit --restart=unless-stopped backend

clear-old-deploy: docker-build
	-docker kill back_01 
	-docker rm back_01

docker-build: mvn-clean-package
	docker build -t backend .

mvn-clean-package:
	mvn clean package

# ---------------------------------------------------------------
# Deploy only
# ---------------------------------------------------------------
deploy-only: clear-old-deploy-only
	@echo ""
	@echo "Docker start container."
	-docker run -p 8801:80 --name back_01 -dit --restart=unless-stopped backend 

clear-old-deploy-only: docker-build-only
	@echo ""
	@echo "Docker killing Process."
	-docker kill back_01
	-docker rm back_01

docker-build-only:
	@echo ""
	@echo "Docker Building Process."
	docker build -t backend .

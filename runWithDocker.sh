INPUT_FILE_PATH=$1
echo "---------------- BUILDING DOCKER IMAGE ---------------"
docker build -t jbaba/root-api:1.0-SNAPSHOT .
echo "----------------FINISHED BUILDING DOCKER IMAGE ---------------"

echo "---------------- RUNNING APPLICATION -----------------"
docker run -v $INPUT_FILE_PATH:/app/input.txt jbaba/root-api:1.0-SNAPSHOT

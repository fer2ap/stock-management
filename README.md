# stock-management
Stock management REST API built with MySQL, Maven and Spring Boot

# Running the project on docker

This project spins up two docker containers:
  
  - app: It's a Spring Boot application
  - db: It's a MySQL database

In order to run this project on you local machine, you should install Docker and docker-compose on your machine using the official documentation on https://docs.docker.com/get-docker/ and https://docs.docker.com/compose/install/ .

Docker will be responsible for running the containers listed above. After setting up Docker on you machine, clone this repository and execute the following command:

`docker-compose up -d`

docker-compose will read the configuration files: Dockerfile and docker-compose.yaml. The containers and volumes listed on the docker-compose.yaml are used to spin up the containers accordingly.

The container app uses the cloned repository as a volume. Using this strategy you can edit the files inside the project directory while the application is running on the container. As a standart for this project, the port 8080 on the container is mapped to your localhost:8080

After building and running all the images with docker-compose, you'll need to access the app container in order to run the application there. You can do this with the following command:

`docker exec -it stock-management_app_1 bash`

Inside the app container you can access the spring boot application directory. This location is configured at the docker-compose.yaml file. The standart workdir is set to "/app" and the absolute path to the spring boot app is "/app/src". Run the following command to access the src dir:

`cd /app/src`

Before running the project, you'll need to give execution permision to the .mvnw files with the command:

`chmod +x mvnw` 

And run the project with which will install all the project dependencies and run the application at the port 8080:

`./mvnw spring-boot:run`

# MODELS

For this project, the MySQL DB was designed with 2 tables: a Stock table containing a unique attribute name that's also the primary key and the Quotes table. Each Quote has the attribute float value and is mapped by the column stock_id to a stock. A quote must not be orphan and the DB was configured to delete all orphans Quote.

# ENDPOINTS

### Check out screenhots folder for exemples

Seach for all the stocks and its quotes in the MySQL DB

HTTP Method: GET

Endpoint: http://localhost:8080/stock

Request body: none

Return type: List<Map<String, Object>>

[

    {
    
        "name": STOCK_1_NAME,
        
        "quotes": [
        
            quote_value_1,
            
            quote_value_2
            
        ]
        
    }
    
]

-----

Seach for the stock with the given name and its quotes in the MySQL DB

HTTP Method: GET

Endpoint: http://localhost:8080/stock?name=<STOCK_NAME>

Request body: none

Return type: List<Map<String, Object>>

[

    {
    
        "name": STOCK_NAME,
        
        "quotes": [
        
            quote_value_1,
            
            quote_value_2,
            
            quote_value_3
            
        ]
        
    }
    
]

-----

Create a stock with the given unique name and its quotes in the MySQL DB. The quotes on the request body is optional and may be ommited.

HTTP Method: POST

Endpoint: http://localhost:8080/stock

Request body: 

[

    {
    
        "name": STOCK_NAME,
        
        "quotes": [
        
            quote_value_1,
            
            quote_value_2,
            
            quote_value_3
            
        ]
        
    }
    
]

Response type: List<Map<String, Object>>

[

    {
    
        "name": STOCK_NAME,
        
        "quotes": [
        
            quote_value_1,
            
            quote_value_2,
            
            quote_value_3
            
        ]
        
    }
    
]

-----

Update a stock with the given name and adds quotes to the MySQL DB.

The quotes on the request body are required for this resource.

HTTP Method: PATCH

Endpoint:  http://localhost:8080/stock/<STOCK_NAME>

Request body: 

[

    {
            
        "quotes": [
        
            quote_value_4,
            
            quote_value_5,
            
            quote_value_6
            
        ]
        
    }
    
]

Response type: List<Map<String, Object>>

[

    {
    
        "name": STOCK_NAME,
        
        "quotes": [
        
            quote_value_1,
            
            quote_value_2,
            
            quote_value_3,
            
            quote_value_4,
            
            quote_value_5,
            
            quote_value_6
            
        ]
        
    }
    
]
      
-----

Delete a stock with the given name and also its quotes.

HTTP Method: DELETE

Endpoint: http://localhost:8080/stock/<STOCK_NAME>

Request body: none

Response body: none
 

[Prototype] Spreadsheet Files Hosting REST API using POSTGRESQL as DB

Frameworks/Libraries used
- hateoas
- spring mvc
- apache.poi
- jackson
- commons-fileupload
- hibernate
- spring-data-jpa

API consuming: 
  - acceptable-types:
    - XLS,
    - XLSX

  - endpoints:
     - GET: 
      - all-files: 
        - URL: http://spreadsheethostingrestapi-env.eba-rmw59rni.us-east-1.elasticbeanstalk.com/spreadsheet-files
      - single-file:
        - URL: http://spreadsheethostingrestapi-env.eba-rmw59rni.us-east-1.elasticbeanstalk.com/spreadsheet-files/id
      - download-file
        - URL: http://spreadsheethostingrestapi-env.eba-rmw59rni.us-east-1.elasticbeanstalk.com/spreadsheet-files/download/id
    - POST: 
        - URL: http://spreadsheethostingrestapi-env.eba-rmw59rni.us-east-1.elasticbeanstalk.com/spreadsheet-files
    - PUT: 
        - URL: http://spreadsheethostingrestapi-env.eba-rmw59rni.us-east-1.elasticbeanstalk.com/spreadsheet-files/id
    - DELETE: 
        - URL: http://spreadsheethostingrestapi-env.eba-rmw59rni.us-east-1.elasticbeanstalk.com/spreadsheet-files/id

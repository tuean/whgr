### base
a website for demonstration by vue3 and tailwind

### plugins

* [vuex-persistedstate](https://github.com/robinvdvleuten/vuex-persistedstate) for data persist in vuex

* [mitt](https://github.com/developit/mitt) for event bus

### wujie

* cors
    ```
        add_header Access-Control-Allow-Origin * always; 
        add_header Access-Control-Allow-Methods GET,POST,PUT,DELETE,OPTIONS always;
        add_header Access-Control-Allow-Credentials true always;
        add_header Access-Control-Allow-Headers DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,x-auth-token always;
        add_header Access-Control-Max-Age 1728000 always;

        if ($request_method = OPTIONS) {
            return 200;
        }
    ```


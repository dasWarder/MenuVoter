<h2> Menu voter</h2>
<h3> A simple application to manage a restaurants list, create daily menu for it and voting this menu</h3>

<b>Stack:</b>
<li><b>Backend</b></li>
<li>Spring Boot</li>
<li>Data jpa</li>
<li>Data mongoDB</li>
<li>Spring scheduler</li>
<li>DB: PostgreSQL, MongoDB</li>
<li>Liquibase</li>
<li>Docker + docker-compose</li>
<li>JUnit</li>
<li>Jackson databind</li>
<li>JPA/Hibernate</li>
<li>Hazelcast</li>
<li>Lombok</li>
<li>Springdoc openapi ui</li>
<li>Embedded H2, Mongo DB</li>
<li><b>Frontend</b></li>
<li>React JS</li>
<li>Axios</li>
<hr>
<h2>Main points for working:</h2>


<h3>To build and update project:</h3>
<p>(stop an old container if it's running, build, start a new container, update databases by Liquibase)</p>

    bash start.sh
<hr>

<h3>To stop container and clean build:</h3>
    
    bash stop.sh
<hr>

<h3>Restaurant</h3>
<p>
    To receive a list of all restaurants (<b><i>GET</i> request</b>): 

    hostname:port/restaurants
</p>


<p>
    To receive a one restaurant by its ID (<b><i>GET</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId}
</p>


<p>
    To receive a one restaurant by its NAME (<b><i>GET</i> request</b>):

    hostname:port/restaurants/restaurant + parameter name

    Parameter name: string
</p>


<p>
    To create a new restaurant (<b><i>POST</i> request</b>):

    hostname:port/restaurants/restaurant + a restaurant body

    Restaurant body:
    {
        title: string,
        description: string
    }
</p>


<p>
    To update a restaurant by its ID (<b><i>PUT</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId} + a new restaurant body

    Restaurant body:
    {
        title: string,
        description: string
    }
</p>


<p>
    To delete a restaurant by its ID (<b><i>DELETE</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId}
</p>
<hr>

<h3>Menu</h3>
<p>
    To receive all menus for a restaurant (<b><i>GET</i> request</b>):
    
    hostname:port/restaurants/restaurant/{restaurantId}/menus
</p>

<p>
    To receive today's menu for a restaurant (<b><i>GET</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId}/menus/menu
</p>

<p>
    To receive a menu for a restaurant by creating date (<b><i>GET</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId}/menus/menu + parameter date

    parameter date: 
    yyyy-MM-dd
</p>

<p>
    To store a menu for a restaurant (<b><i>POST</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId}/menus/menu + menu body
    
    Menu body:
    {
        creating_date: LocalDate,
        dishes: array
            [
                {
                    title: string,
                    description: string
                },  
                ...
            ]
    }
</p>

<p>
    To update a menu for a restaurant (<b><i>PUT</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId}/menus/menu/{menuId} + a new menu body
    
    Menu body:
    {
        creating_date: LocalDate,
        dishes: array
            [
                {
                    title: string,
                    description: string
                },  
                ...
            ]
    }
</p>

<p>
    To delete a menu for a restaurant (<b><i>DELETE</i> request</b>):

    hostname:port/restaurants/restaurant/{restaurantId}/menus/menu/{menuId}
</p>
<hr>

<h3>Rating</h3>

<p>
    To vote for a menu (<b><i>PUT</i> request</b>):
    
    hostname:port/restaurants/restaurant/{restaurantId}/menus/menu/rate + vote body
    
    Vote body:
    {
        menu_id: string,
        email: string,
        rate: double
    }
</p>

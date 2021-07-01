import React, { Component } from "react";
import RestaurantService from "../../service/RestaurantService";


class RestaurantsListComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            restaurants:[]
        };

        this.getDailyMenuByRestaurantId = this.getDailyMenuByRestaurantId.bind(this);
    }

    componentDidMount() {

        RestaurantService.getAllRestaurants().then(response => {
            console.log('Get response from the server ', response.data);

            this.setState({
                restaurants: response.data
            })
        });
    }

    getDailyMenuByRestaurantId(id) {

        console.log('Redirect to /restaurants/restaurant/' + id + '/menus/menu');

        this.props.history.push(`/restaurants/restaurant/${id}/menus/menu`);
    }

    backPrev() {

        console.log("Redirect to /");

        this.props.history.push("/");
    }

    render() {
        return (
            <div>
                <div className={ "row text-start col-md-12" }>
                    <a onClick={ () => this.backPrev() } className={ "lead" }>Back</a>
                </div>
                <div className={ "row" }>
                    <h2 className={ "display-3" }>Today's restaurant</h2>
                    <p>A list of restaurants which menus are available today for your voting</p>
                    {
                        this.state.restaurants.map(
                            restaurant =>
                                <div className="card my-4 mx-4 col-md-5 alert alert-info" key={ restaurant.id }>
                                    <img src="https://i.stack.imgur.com/y9DpT.jpg"
                                         width="500" height="300" className="img-thumbnail my-3" alt="restaurant_img"/>
                                        <div className="card-body text-center">
                                            <h5 className="card-title display-6 text-success"> { restaurant.title }</h5>
                                            <p className="card-text"><i>{ restaurant.description }</i></p>
                                            <button onClick={ () => this.getDailyMenuByRestaurantId(restaurant.id) }
                                                    className="btn btn-primary col-md-4">Menu</button>
                                        </div>
                                </div>
                        )
                    }
                </div>
            </div>
        )
    }
}

export default RestaurantsListComponent;
import React, { Component } from "react";
import MenuService from "../../service/MenuService";
import RestaurantService from "../../service/RestaurantService";



class TodayMenuComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            restaurant_id: this.props.match.params.id,
            restaurant_title: '',
            id: '',
            creating_date: '',
            dishes: [],
            rate: 0.0,
            votes_count: 0
        };

        console.log('Restaurant id from constructor: ', this.state.restaurant_id);
    }

    componentDidMount() {
        MenuService.getMenuForToday(this.state.restaurant_id).then(response => {
            let menu = response.data;

            console.log('Response for menu: ', menu);

            this.setState({
                id: menu.id,
                creating_date: menu.creating_date,
                dishes: menu.dishes,
                rate: menu.rate,
                votes_count: menu.votes_count
            })
        })

        this.getRestaurantById(this.state.restaurant_id);
    }

    getRestaurantById(restaurant_id) {
        RestaurantService.getRestaurantById(restaurant_id).then(response => {
            let restaurant = response.data;

            this.setState({
                restaurant_title: restaurant.title
            })
        })

    }

    showVotePage(restaurant_id, menu_id) {
        console.log("Redirect to /restaurants/restaurant/:id/menus/menu/rate for a restaurant with ID = ", restaurant_id);

        this.props.history.push(`/restaurants/restaurant/${restaurant_id}/menus/menu/${menu_id}/rate`);
    }


    getMenu() {

        if (this.state.creating_date === '') {
           return (
               <div className={ "row" } >
                <p className={ "lead"}>
                    This restaurant doesn't have a menu for today
                </p>
            </div>
           )
        } else {
            return (
                <div className={ "row" }>
                    <p className={ "text-secondary" }><i>Date of menu: { this.state.creating_date }</i></p>
                    <div className="card mb-3 col-md-12 alert alert-warning">
                        <div className="row g-0">
                            <div className="col-md-4">
                                <img src="https://i.stack.imgur.com/y9DpT.jpg" className="card-img my-4"
                                     alt="menu_img"/>
                            </div>
                            <div className="col-md-8">
                                <div className="card-body">
                                    <h5 className="card-title display-6"><b>Current rate:</b></h5>
                                    <p className="card-text display-6 ">{ this.state.rate } of 10</p>
                                    <p className="card-text"><small className="text-muted">Number of votes: { this.state.votes_count }</small></p>
                                    <button onClick={() => this.showVotePage(this.state.restaurant_id, this.state.id) } className={ "btn btn-primary col-md-4" }>Vote</button>
                                </div>
                            </div>
                        </div>
                        <hr/>
                        <div className={ "row" }>
                                {
                                    this.state.dishes.map(dish =>
                                        <div className={ "row text-center" }>
                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td className={ "lead"}>
                                                            <b className={ "h4" }>
                                                                { dish.title }
                                                            </b>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <p className={ "lead mb-4" }><i> { dish.description } </i></p>
                                        </div>
                                    )
                                }
                        </div>
                    </div>
                </div>
            )
        }
    }


    render() {
        return (
            <div className={ "row" }>
                <p className={ "display-3" }>
                    {
                        this.state.restaurant_title
                    } menu
                </p>
                {
                    this.getMenu()
                }
            </div>
        )
    }
}


export default TodayMenuComponent;
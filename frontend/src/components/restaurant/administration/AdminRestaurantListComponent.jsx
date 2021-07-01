import React, {Component} from "react";
import RestaurantService from "../../../service/RestaurantService";

class AdminRestaurantListComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            restaurants: []
        }

        this.editRestaurant = this.editRestaurant.bind(this);
        this.addRestaurant = this.addRestaurant.bind(this);
        this.deleteRestaurantById = this.deleteRestaurantById.bind(this);
    }

    componentDidMount() {
        RestaurantService.getAllRestaurants().then(response => {
            this.setState({
                restaurants: response.data
            })
        })
    }

    addRestaurant() {

        console.log('Redirect to /admin/restaurants/restaurant/:id');

        this.props.history.push(`/admin/restaurants/restaurant/` + null);
    }

    editRestaurant(id) {

        console.log('Redirect to /admin/restaurants/restaurant/', id);

        this.props.history.push(`/admin/restaurants/restaurant/${id}`);
    }

    deleteRestaurantById(id) {

        console.log('Delete restaurant with ID=', id);

        RestaurantService.deleteRestaurantById(id).then(
            window.location.reload()
        )
    }

    editMenu(restaurant_id) {

        console.log('Redirect to /admin/restaurants/restaurant/' + restaurant_id + '/menu');

        this.props.history.push(`/admin/restaurants/restaurant/${restaurant_id}/menu`);

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
                    <h1 className={ "display-4" }>Restaurants list</h1>
                    <div className={ "row" }>
                        <div className={ "col-sm" }>
                            <button className={ "btn btn-primary btn-success my-3" } onClick={ this.addRestaurant }>Create a restaurant</button>
                        </div>
                    </div>
                    <table className={ "table table-bordered" }>
                        <thead className={ "bg-warning" }>
                            <tr>
                                <th>Title</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody className={ "table-success" }>
                        {
                            this.state.restaurants.map(restaurant =>
                                <tr key={ restaurant.id }>
                                    <td>{ restaurant.title }</td>
                                    <td>{ restaurant.description }</td>
                                    <td>
                                        <button className={ "btn btn-info mx-2"} onClick={ () => this.editMenu(restaurant.id)}>Menu</button>
                                        <button onClick={ () => this.deleteRestaurantById(restaurant.id) } className={ "btn btn-danger mx-2"}>Delete</button>
                                        <button onClick={ () => this.editRestaurant(restaurant.id) } className={ "btn btn-success mx-2"}>Update</button>
                                    </td>
                                </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default AdminRestaurantListComponent;
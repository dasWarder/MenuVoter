import React, { Component } from "react";
import RestaurantService from "../../../service/RestaurantService";

class CreateRestaurantComponent extends Component {


    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            title: '',
            description: ''
        }

        this.changeTitleHandler = this.changeTitleHandler.bind(this);
        this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
        this.createOrUpdateRestaurant = this.createOrUpdateRestaurant.bind(this);
        this.cancel = this.cancel.bind(this);
        this.redirectAndReload = this.redirectAndReload.bind(this);
    }

    componentDidMount() {

        if(this.state.id === 'null') {

            return;

        } else {

            RestaurantService.getRestaurantById(this.state.id).then(response => {
                let restaurantById = response.data;

                this.setState({
                    title: restaurantById.title,
                    description: restaurantById.description,
                });
            });
        }
    }

    createOrUpdateRestaurant = (r) => {
        r.preventDefault();

        let restaurant = {
            title: this.state.title,
            description: this.state.description
        };

        if(this.state.id === 'null') {

            RestaurantService.createRestaurant(restaurant).then(

                this.redirectAndReload()
            );

        } else {

            RestaurantService.updateRestaurant(this.state.id, restaurant).then(() =>

                    this.redirectAndReload()
            );

        }
    }


    changeTitleHandler = (event) => {

        this.setState({
            title: event.target.value
        })
    }

    changeDescriptionHandler = (event) => {

        this.setState( {
            description: event.target.value
        })
    }

    cancel() {

        console.log('Canceling creating or updating');

        this.props.history.push('/admin/restaurants');
    }

    redirectAndReload() {

        this.cancel();

        window.location.reload();
    }

    getTitle() {

        if (this.state.id === 'null') {

            return 'Create';

        } else {

            return 'Update';

        }
    }


    render() {
        return (
            <div className={ "row" }>
                    <div className={ "card col-md-4 offset-md-4 p-3" }>
                        <h3 className={ "text text-center text-primary mb-2" }>
                            {
                                this.getTitle()
                            } restaurant
                        </h3>
                        <div className={ "row" }>
                            <div className={ "card-body" }>
                                <form>
                                    <div className={ "form-group" }>
                                        <div className={ "mb-3" }>
                                            <label className={ "mb-1" }>Title</label>
                                            <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                                   value={ this.state.title } onChange={ this.changeTitleHandler }/>
                                        </div>
                                        <div className={ "mb-3" }>
                                            <label className={ "mb-1" }>Description</label>
                                            <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Restaurant description" }
                                                   value={ this.state.description } onChange={ this.changeDescriptionHandler } />
                                        </div>
                                        <button type="submit" className="btn btn-success col-md-4 mx-1" onClick={ this.createOrUpdateRestaurant }>
                                            {
                                                this.getTitle()
                                            }
                                        </button>
                                        <button type="submit" className="btn btn-danger col-md-3 mx-1"
                                                onClick={ this.cancel }>Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
            </div>
        )
    }
}

export default CreateRestaurantComponent;
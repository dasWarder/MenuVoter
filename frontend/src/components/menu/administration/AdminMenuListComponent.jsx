import React, { Component } from "react";
import MenuService from "../../../service/MenuService";



class AdminMenuListComponent extends Component {


    constructor(props) {
        super(props);

        this.state = {
            id: '',
            restaurant_id: this.props.match.params.restaurant_id,
            creating_date: '',
            dishes: [],
            rate: 1,
            votes_count: 0
        }

        this.getListOfAllMenus = this.getListOfAllMenus.bind(this);
        this.deleteMenuById = this.deleteMenuById.bind(this);
    }

    componentDidMount() {

        MenuService.getMenuForToday(this.state.restaurant_id).then(response => {

            let menu = response.data;

            if(menu === 'Menu not found') {

                this.setState({})

            } else  {

                this.setState( {
                    id: menu.id,
                    creating_date: menu.creating_date,
                    dishes: menu.dishes,
                    rate: menu.rate,
                    votes_count: menu.votes_count
                })

            }
        })
    }

    getListOfAllMenus(restaurant_id) {

        console.log("Redirect to /admin/restaurants/restaurant/:restaurant_id/menu/menus");

        this.props.history.push(`/admin/restaurants/restaurant/${restaurant_id}/menu/menus`);
    }

    createOrUpdateMenu(restaurant_id) {

        console.log("Redirect to /admin/restaurants/restaurant/:restaurant_id/menu/:menu_id")

        if(this.state.id === '') {

            this.props.history.push(`/admin/restaurants/restaurant/${restaurant_id}/menu/` + null);

        } else {

            this.props.history.push(`/admin/restaurants/restaurant/${restaurant_id}/menu/${this.state.id}`);

        }


    }

    deleteMenuById(restaurant_id, menu_id) {

        MenuService.deleteMenuById(restaurant_id, menu_id).then(

            window.location.reload()
        )
    }

    getTitle() {

        if(this.state.id === '') {

            return "Create a new";

        } else {

            return "Update current";
        }
    }

    getDeleteButton() {

        if(this.state.id === '') {

            return;

        } else {

            return (
                    <button onClick={() => this.deleteMenuById(this.state.restaurant_id, this.state.id)}
                            className={ "btn btn-danger my-2 mx-2" }>Delete current</button>
            )
        }
    }


    getMenuDetailsOrMessage() {

        if (this.state.id === '') {
            return (
                <p className={ "lead" }>There is no actual menu for today</p>
            )
        } else {
            return (
                <div className={ "row" }>
                    <div className={ "col-md-8" }>
                    {
                        this.state.dishes.map(dish =>
                            <div className={"row text-center alert-warning text-dark"} key={ dish.title }>
                                <table>
                                    <tbody>
                                    <tr>
                                        <td className={"lead"}>
                                            <b className={"h4"}>
                                                {dish.title}
                                            </b>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <p className={"lead mb-4"}><i> {dish.description} </i></p>
                            </div>
                        )
                    }
                    </div>
                </div>

            )
        }
    }

    backPrev() {

        console.log("Redirect to /admin/restaurants");

        this.props.history.push("/admin/restaurants");
    }

    render() {
        return (
            <div>
                <div className={ "row text-start col-md-12" }>
                    <a onClick={ () => this.backPrev() } className={ "lead" }>Back</a>
                </div>
                <div className={ "row" }>
                    <p className={ "display-4" }>Active menu:</p>
                    {
                        this.getMenuDetailsOrMessage()
                    }
                    <div className={ "col-md-12" }>
                        <div className={ "col-md-12" }>
                                <button onClick={ () => this.getListOfAllMenus(this.state.restaurant_id) }
                                        className={ "btn btn-success my-2 mx-2" }>List of all</button>
                                <button onClick={ () => this.createOrUpdateMenu(this.state.restaurant_id) }
                                        className={ "btn btn-warning my-2 mx-2" }>{ this.getTitle() }</button>
                                { this.getDeleteButton() }
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}


export default AdminMenuListComponent;
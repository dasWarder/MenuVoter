import React, { Component } from "react";
import MenuService from "../../../service/MenuService";


class AllMenusListComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            restaurant_id: this.props.match.params.restaurant_id,
            menus:[]
        }
    }

    componentDidMount() {

        MenuService.getAllMenus(this.state.restaurant_id).then(response => {
            this.setState({
                menus: response.data
            })
        })
    }


    deleteMenuById(restaurant_id, menu_id) {

        MenuService.deleteMenuById(restaurant_id, menu_id).then(

            window.location.reload()
        )
    }


    render() {
        return (
                <table className={ "table col-md-12 offset-md-12" }>
                    <thead>
                        <tr>
                            <th>Creating date</th>
                            <th>Dishes</th>
                            <th>Rate</th>
                            <th>Votes</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>

                            {
                                this.state.menus.map(menu =>
                                    <tr key={ menu.id }>
                                        <td>{ menu.creating_date }</td>
                                        <td>{ menu.dishes.map(dish =>
                                            <div className={ "row" } key={ dish.title }>
                                                <p><b>{ dish.title } :</b> { dish.description }</p>
                                            </div>
                                        ) }</td>
                                        <td>{ menu.rate }</td>
                                        <td>{ menu.votes_count }</td>
                                        <td>
                                            <button onClick={ () => this.deleteMenuById(this.state.restaurant_id, menu.id) }
                                                    className={ "btn btn-danger btn-sm bg-danger my-1" }>Delete</button>
                                        </td>
                                    </tr>
                                )
                            }
                    </tbody>
                </table>
        )
    }
}

export default AllMenusListComponent;
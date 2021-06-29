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


    render() {
        return (
                <table className={ "table col-md-12 offset-md-12" }>
                    <thead>
                        <tr>
                            <th>Creating date</th>
                            <th>Dishes</th>
                            <th>Rate</th>
                            <th>Votes</th>
                        </tr>
                    </thead>
                    <tbody>

                            {
                                this.state.menus.map(menu =>
                                    <tr key={ menu.id }>
                                        <td>{ menu.creating_date }</td>
                                        <td>{ menu.dishes.map(dish =>
                                            <div className={ "row" }>
                                                <p><b>{ dish.title } :</b> { dish.description }</p>
                                            </div>
                                        ) }</td>
                                        <td>{ menu.rate }</td>
                                        <td>{ menu.votes_count }</td>
                                    </tr>
                                )
                            }
                    </tbody>
                </table>
        )
    }
}

export default AllMenusListComponent;
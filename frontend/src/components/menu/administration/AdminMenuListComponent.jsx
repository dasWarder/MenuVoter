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
    }

    componentDidMount() {

        MenuService.getMenuForToday(this.state.restaurant_id).then(response => {

            let menu = response.data;

            this.setState( {
                id: menu.id,
                creating_date: menu.creating_date,
                dishes: menu.dishes,
                rate: menu.rate,
                votes_count: menu.votes_count
            })
        })
    }

    getMenuDetailsOrMessage() {

        if(this.state.id === '') {
            return (
                <p className={ "lead" }>There is no actual menu for today</p>
            )
        } else {
            return (
                <div className={ "row" }>
                    <div className={ "col-md-8" }>
                    {
                        this.state.dishes.map(dish =>
                            <div className={"row text-center alert-warning text-dark"}>
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
                    <div className={ "col-md-4" }>
                        <div className={ "col-md-12" }>
                            <div className={ "col" }>
                                <button className={ "btn btn-success my-2" }>List of all</button>
                            </div>
                            <div className={ "col" }>
                                <button className={ "btn btn-warning my-2" }>Update current</button>
                            </div>
                            <div className={ "col" }>
                                <button className={ "btn btn-success my-2"}>Create new one</button>
                            </div>
                            <div className={ "col" }>
                                <button className={ "btn btn-danger my-2"}>Delete current</button>
                            </div>

                        </div>
                    </div>
                </div>

            )
        }
    }

    render() {
        return (
            <div className={ "row" }>
                <p className={ "display-4" }>Active menu:</p>
                {
                    this.getMenuDetailsOrMessage()
                }
            </div>
        )
    }

}


export default AdminMenuListComponent;
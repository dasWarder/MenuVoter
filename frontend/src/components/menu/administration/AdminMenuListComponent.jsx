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
                    {
                        this.state.dishes.map(dish =>
                            <div className={"row text-center"}>
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
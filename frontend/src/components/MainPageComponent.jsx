import React,{ Component } from "react";

class MainPageComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {

        }
    }

    enterAsAdmin() {

        console.log("Enter as admin");

        this.props.history.push("/admin/restaurants");
    }

    enterAsUser() {

        console.log("Enter as user");

        this.props.history.push("/restaurants");
    }


    render() {
        return (
            <div className={ "container text-center" }>
                <div className={ "row" }>
                    <p className={ "display-4" }>Select role</p>
                </div>
                <div className={ "row col-md-4 offset-md-4" }>
                    <a onClick={ () => this.enterAsAdmin() } className={ "btn btn-warning my-2" }>Admin</a>
                    <a onClick={ ()=> this.enterAsUser() } className={ "btn btn-success my-2" }>User</a>
                </div>
            </div>
        )
    }
}

export default MainPageComponent;
import React,{ Component } from "react";
import MenuService from "../../../service/MenuService";

class AdminCreateMenu extends Component {

    constructor(props) {
        super(props);

        this.state = {
            menu_id: this.props.match.params.menu_id,
            restaurant_id: this.props.match.params.restaurant_id,
            dishes: [
                {
                    title: '',
                    description: ''
                },
                {
                    title: '',
                    description: ''
                },
                {
                    title: '',
                    description: ''
                },
                {
                    title: '',
                    description: ''
                }
            ]
        }

        this.createOrUpdateMenu = this.createOrUpdateMenu.bind(this);
    }

    componentDidMount() {

        if (this.state.menu_id === 'null') {

            return;

        } else {

            console.log("Receive a today's menu for updating");

            MenuService.getMenuById(this.state.restaurant_id, this.state.menu_id).then(response => {

                let todayMenu = response.data;

                console.log("ID: ", todayMenu.id);

                this.setState( {
                    menu_id: todayMenu.id,
                    dishes: todayMenu.dishes
                })
            })
        }
    }

    createOrUpdateMenu = (m) => {

        m.preventDefault();

        if (this.state.menu_id === 'null') {

            console.log("Redirect to create menu");

            MenuService.createTodayMenu(this.state.restaurant_id, this.state.dishes);

        } else {

            console.log("Redirect to update menu");

            MenuService.updateTodayMenu(this.state.restaurant_id, this.state.menu_id, this.state.dishes);

        }

        this.handleCloseAndReload();
    }


    titleHandler(title, index) {

        console.log(`For index ${index} change title to ${title.target.value}`);

        let temp = this.state.dishes;
        temp[index].title = title.target.value;

        this.setState({
            dishes: temp
        })
    }

    descriptionHandler(description, index) {

        console.log(`For index ${index} change description to ${description.target.value}`);

        let temp = this.state.dishes;
        temp[index].description = description.target.value;

        this.setState({
            dishes: temp
        })
    }


    handleClose() {

        console.log(`Redirect to /admin/restaurants/restaurant/${this.state.restaurant_id}/menu`);

        this.props.history.push(`/admin/restaurants/restaurant/${this.state.restaurant_id}/menu`);
    }

    handleCloseAndReload() {

        this.handleClose();

        window.location.reload();
    }

    getTitle() {
        if(this.state.menu_id === 'null') {

            return 'Create';

        } else {

            return 'Update';
        }
    }


    render() {
        return(
            <div className={ "row" }>
                <div className={ "card col-md-5 offset-md-4 p-3" }>
                    <h3 className={ "text text-center text-primary mb-2" }>
                        {
                            this.getTitle()
                        } menu for today
                    </h3>
                    <div className={ "row" }>
                        <div className={ "card-body col-md-12" }>
                            <form>
                                <div className={ "form-group" }>
                                    <p className={ "lead" }><b>First course</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                               onChange={(e) => this.titleHandler(e, 0)}
                                               value={ this.state.dishes[0].title }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                               onChange={(e) => this.descriptionHandler(e, 0)}
                                               value={ this.state.dishes[0].description }/>
                                    </div>
                                    <p className={ "lead" }><b>Second course</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                               onChange={(e) => this.titleHandler(e, 1)}
                                               value={ this.state.dishes[1].title }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                               onChange={(e) => this.descriptionHandler(e, 1)}
                                               value={ this.state.dishes[1].description }/>
                                    </div>
                                    <p className={ "lead" }><b>Dessert</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                               onChange={(e) => this.titleHandler(e, 2)}
                                               value={ this.state.dishes[2].title }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                               onChange={(e) => this.descriptionHandler(e, 2)}
                                               value={ this.state.dishes[2].description }/>
                                    </div>
                                    <p className={ "lead" }><b>Beverage</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                               onChange={(e) => this.titleHandler(e, 3)}
                                               value={ this.state.dishes[3].title }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                               onChange={(e) => this.descriptionHandler(e, 3)}
                                               value={ this.state.dishes[3].description }/>
                                    </div>
                                    <button onClick={ this.createOrUpdateMenu } type="submit" className="btn btn-success col-md-3 mx-1">
                                        {
                                            this.getTitle()
                                        }
                                    </button>
                                    <button onClick={ () => this.handleClose() } type="submit" className="btn btn-danger col-md-3 mx-1">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default AdminCreateMenu;

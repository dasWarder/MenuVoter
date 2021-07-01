import React,{ Component } from "react";
import MenuService from "../../../service/MenuService";

class AdminCreateMenu extends Component {

    constructor(props) {
        super(props);

        this.state = {
            menu_id: this.props.match.params.menu_id,
            restaurant_id: this.props.match.params.restaurant_id,
            dishes: [],
            dish: {
                title: '',
                description: ''
            }
        }

        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);

    }

    componentDidMount() {

        if (this.state.menu_id === 'null') {

            return;

        } else {
            MenuService.getMenuForToday(this.state.restaurant_id).then(response => {
                let menu = response.data;
                let  item = [...this.state.dishes];

                item.push({
                    dish: this.state.dish
                });

                this.setState({
                    item,
                    dish: {
                        title: '',
                        description: ''
                    }
                });
            })
        }
    }

    handleTitleChange=(e) => {
        let temp = this.state.dish;
        temp.title = e.title;

        this.setState({
            dish: temp
        });
    }

    handleDescriptionChange=(e) => {
        let temp = this.state.dish;
        temp.description = e.description;

        this.setState({
            dish: temp
        });
    }

    createMenu = (m) => {
        m.preventDefault();

        let  item = [...this.state.dishes];

        item.push({
            dish: this.state.dish
        });

        this.setState({
            item,
            dish: {
                title: '',
                description: ''
            }
        });

        MenuService.createTodayMenu(this.state.restaurant_id, this.state.dishes).then(response => {
            console.log("Resposne: ", response);
        })
    }


    handleClose() {

        console.log(`Redirect to /admin/restaurants/restaurant/${this.state.restaurant_id}/menu`);

        this.props.history.push(`/admin/restaurants/restaurant/${this.state.restaurant_id}/menu`);
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
                                            onChange={ this.handleTitleChange }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                            onChange={ this.handleDescriptionChange }/>
                                    </div>
                                    <p className={ "lead" }><b>Second course</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                            onChange={ this.handleTitleChange }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                            onChange={ this.handleDescriptionChange }/>
                                    </div>
                                    <p className={ "lead" }><b>Dessert</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                            onChange={ this.handleTitleChange }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                            onChange={ this.handleDescriptionChange }/>
                                    </div>
                                    <p className={ "lead" }><b>Beverage</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }
                                            onChange={ this.handleTitleChange }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }
                                            onChange={ this.handleDescriptionChange }/>
                                    </div>
                                    <button onClick={ this.handleFormSubmit } type="submit" className="btn btn-success col-md-3 mx-1">
                                        {
                                            this.getTitle()
                                        }
                                    </button>
                                    <button onClick={ () =>this.handleClose() } type="submit" className="btn btn-danger col-md-3 mx-1">Cancel</button>
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

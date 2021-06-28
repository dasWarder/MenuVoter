import React, { Component } from "react";
import VoteService from "../../service/VoteService";
import Modal from "react-bootstrap/Modal";
import ModalBody from "react-bootstrap/ModalBody";
import ModalHeader from "react-bootstrap/ModalHeader";
import ModalFooter from "react-bootstrap/ModalFooter";
import ModalTitle from "react-bootstrap/ModalTitle";
import xtype from 'xtypejs';


class VoteComponent extends Component {


    constructor(props) {
        super(props);

        this.state = {
            restaurant_id: this.props.match.params.restaurant_id,
            menu_id: this.props.match.params.menu_id,
            email: '',
            rate: 1,
            response_message: '',
            isOpen: false,
            error: [],

        }

        this.emailHandler = this.emailHandler.bind(this);
        this.rateHandler = this.rateHandler.bind(this);
        this.vote = this.vote.bind(this);
        this.cancel = this.cancel.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleOpen = this.handleOpen.bind(this);
    }

    emailHandler = (event) => {
        this.setState( {
            email: event.target.value
        })
    }

    rateHandler = (event) => {
        this.setState( {
            rate: event.target.value
        })
    }

    vote(restaurant_id) {
        console.log('Vote for a menu');

        let vote = {
            menu_id: this.state.menu_id,
            email: this.state.email,
            rate: this.state.rate
        };


        VoteService.voteForARestaurant(restaurant_id, vote).then(response => {
            console.log('Response for voting= ', response);

            if (xtype.type(response.data) === 'string') {

                this.setState( {
                    response_message: response.data
                })

                this.handleOpen();

            } else {

                this.cancel();

            }
        })
    }


    cancel() {
        console.log('Cancel voting process');

        this.props.history.push("/restaurants");
    }

    handleClose() {
        console.log('isClose');

        this.setState( {
            isOpen: false
        })
    }

    handleOpen() {
        console.log('isOpen');

        this.setState( {
            isOpen: true
        })
    }



    render() {
        return (
            <div className={ "row" }>
                <p className={ "display-4" }>Vote menu</p>
                <small className={ "lead" }>Enter your data below</small>
                <div className={ "card mb-3 col-md-12 alert alert-warning" }>
                    <div className={"card-body"}>
                        <div className={ "my-4" }>
                            <label>Email:</label>
                            <input onChange={ this.emailHandler } className={ "mx-2" } type={ "text" } placeholder={ "Your email" }/>
                        </div>
                        <div className={ "my-4" }>
                            <label>Rate:</label>
                            <select className="mx-3" onChange={ this.rateHandler }>
                                <option value={ this.state.rate }>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                                <option>6</option>
                                <option>7</option>
                                <option>8</option>
                                <option>9</option>
                                <option>10</option>
                            </select>
                        </div>
                        <button onClick = {() => this.vote(this.state.restaurant_id)}
                                value={ this.state.email }
                                type="submit" className="btn btn-primary col-md-3 mx-1">Vote</button>
                        <button onClick={ this.cancel } type="submit" className="btn btn-danger col-md-3 mx-1">Cancel</button>
                        <Modal show={ this.state.isOpen } animation={ false }>
                            <ModalHeader>
                                <ModalTitle>Information</ModalTitle>
                            </ModalHeader>
                            <ModalBody>{ this.state.response_message }</ModalBody>
                            <ModalFooter>
                                <button onClick={ this.handleClose }>Close</button>
                            </ModalFooter>
                        </Modal>
                    </div>
                </div>
            </div>
        )
    }
}

export default VoteComponent;
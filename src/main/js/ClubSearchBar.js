import React, {useState} from 'react';
import "./ClubSearch.css";

const ClubSearchBar = (props) => {

    return (
        <input type="text" value = {props.clubSearchQuery} onChange = {e => props.setClubSearchQuery(e.target.value)}/>
    )
}

export default ClubSearchBar;
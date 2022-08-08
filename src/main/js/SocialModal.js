import React, {useState, useEffect, useRef} from 'react';
import "./ClubEditor.css";
import './ClubSearch.css';
import ClubService from './ClubService';

const SocialModal = (props) => {
    let name = props.currentSocial;
    const [link, setLink] = useState(props.socialMap[name]);
    
    const onDescriptionChange = (e) => {
        setLink(e.target.value);
	};

    const onLinkSubmit = () => {
        ClubService.doAddSocials(name, link).then(response => {props.setRefresh(true)});
        props.setSocialModalIsOpen(false);
        props.setCurrentSocial(null);
    }
    const onLinkRemove = () => {
        ClubService.doRemoveSocials(name, link).then(response => {props.setRefresh(true)});
        props.setSocialModalIsOpen(false);
        props.setCurrentSocial(null);
    }

    return(
        <>
            <h1 className="clubSearchPageH1">Change {props.currentSocial} Link</h1>
            <input value = {link} onChange={onDescriptionChange} className="socialLinkEditor"/>
            <button className = "descChangeButton" onClick = {onLinkSubmit}>Submit</button> 
            <button className = "descChangeButton" onClick = {onLinkRemove}>Remove Link</button> 
        </>
    )

}
export default SocialModal;
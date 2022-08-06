import React, {useState, useEffect, useRef} from 'react';
import "./ClubEditor.css";
import './ClubSearch.css';
import ClubService from './ClubService';

const SocialModal = (props) => {
    let name = props.currentSocial;
    const [link, setLink] = useState(props.socialMap[name]);
    let textAreaRef = useRef(null);
    
    const onDescriptionChange = (e) => {
		textAreaRef.current.style.height = "0px";
		textAreaRef.current.style.height = `${e.target.scrollHeight}px`;
        props.setClubSocials(e.target.value);
        setLink(e.target.value);
	};

    const onLinkSubmit = () => {
        ClubService.doAddSocials(name, link).then(response => {props.setRefresh(true)});
    }
    const onLinkRemove = () => {
        ClubService.doRemoveSocials(name, link).then(response => {props.setRefresh(true)});
    }

    return(
        <>
            <h1 className="clubSearchPageH1">Change {props.currentSocial} Link</h1>
            <textarea value = {link} ref={textAreaRef} onChange={onDescriptionChange} className="clubDescriptionEditor"/>
            <button className = "descChangeButton" onClick = {onLinkSubmit}>Submit</button> 
            <button className = "descChangeButton" onClick = {onLinkRemove}>Remove Link</button> 
        </>
    )

}
export default SocialModal;
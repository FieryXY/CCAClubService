import React, {useState, useEffect} from 'react';
import "./ClubEditor.css";
import './ClubSearch.css';
import ClubService from './ClubService';

const AddTagModal = (props) => {

    const [allTags, setAllTags] = useState([]);
    
    useEffect(() => {
        ClubService.doClubTags().then(response => response.json()).then(json => {
            setAllTags(json);

        }).catch(err => {console.log("backend is not responding")})}, []);

    const toggleTag = (tagName) => {
        if(props.clubTags.includes(tagName)) ClubService.doRemoveTag(tagName).then(response => {props.setRefresh(true)});
        else ClubService.doAddTag(tagName).then(response => {props.setRefresh(true)});
        
    }


    return(
        <>
            <div className = "tagModalBox">
             <h1 className="clubSearchPageH1">Select Tags</h1>
                {allTags.map(clubTags =>{
                let isContainedInClubTags = props.clubTags.includes(clubTags);
                return(
                 <a key = {clubTags} onClick= {() => {toggleTag(clubTags)}} className= {`displayTag ${isContainedInClubTags ? "tagSelected" : "tagUnselected"} clubSearchPageA`}>{clubTags}</a>
                    
                )})}
            </div>
    
        </>
    )
}

export default AddTagModal;
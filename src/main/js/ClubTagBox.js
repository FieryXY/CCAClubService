import React, {useState} from 'react';
import "./ClubSearch.css";

const ClubTagBox = (props) => {
    
    const toggleTag = (tagName) => {
		if(props.clubTagFilters.includes(tagName)) {
            let filteredClubTagFilters = props.clubTagFilters.filter(tag => tag !== tagName);
            props.setClubTagFilters(filteredClubTagFilters);
        }
		else{
            let newClubTagFilters = props.clubTagFilters.concat(tagName);
            props.setClubTagFilters(newClubTagFilters);
        } 
	};

    return(
        <div className = "tags">
             <h1 className="clubSearchPageH1">Tags</h1>
             <div className="tagList">
                {props.allClubTags.map(clubTag =>{

                    let className = (props.clubTagFilters.includes(clubTag)) ? "tag tagSelected clubSearchPageA" : "tag clubSearchPageA"

                    return (<a key = {clubTag} className= {className} onClick={() => toggleTag(clubTag)}> {clubTag} </a>);
                        
                })}
             </div>
        </div>
    )
}
export default ClubTagBox;
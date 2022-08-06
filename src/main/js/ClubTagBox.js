import React, {useState} from 'react';
import "./ClubSearch.css";

const ClubTagBox = (props) => {
    
    const toggleTag = (tagName) => {
		if(props.clubTagFilters.includes(tagName)) {
            const filteredClubTagFilters = props.clubTagFilters.filter(tag => tag !== tagName);
            props.setClubTagFilters(filteredClubTagFilters);
        }
		else{
            const newClubTagFilters = props.clubTagFilters.concat({tagName});
            props.setClubTagFilters(newClubTagFilters);
        } 
	};

    return(
        <div className = "tags">
             <h1 className="clubSearchPageH1">Tags</h1>
                {props.allClubTags.map(clubTags =>{
                    
                 <a key = {clubTags} className= {props.clubTagFilters.includes(clubTags) ? "tag tagSelected clubSearchPageA" : "tag clubSearchPageA"} onClick={() => toggleTag(clubTags)}> {clubTags} </a>
                    
                })}
        </div>
    )
}
export default ClubTagBox;
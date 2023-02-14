import "./ItemBoxTop.css";
import "../../FontAwesome.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ItemBoxTop = (props) => {
  // 현재 선택된 아이콘을 관리하는 state

  return (
    <div className="itemboxtop">
      <img className="itemboxtop-profileImg" src="https://img.youtube.com/vi/N7iIvIV-ZCs/mqdefault.jpg"></img>
        <span className="itembox-bold itemboxtop-id">{props.id}</span>
        <span className="createdAt">{props.createdAt}</span>
    </div>
  );
};

export default ItemBoxTop;

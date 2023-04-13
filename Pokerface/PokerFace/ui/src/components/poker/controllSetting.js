const ControllSetting = (props) => {
    return (
        <div className="form-check border rounded bg-light">
            <input
                className="form-check-input"
                type="checkbox"
                value=""
                id="flexCheckDefault"
            />
            <label className="form-check-label" htmlFor="flexCheckDefault">
                {props.data.cardValue}
            </label>
        </div>
    );
};

export default ControllSetting;

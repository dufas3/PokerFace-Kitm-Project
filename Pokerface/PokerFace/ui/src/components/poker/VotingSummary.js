import './VotingSummary.css'
import '../GlobalCSS.css'

const VotingSummary = (props) => {
    const cards = [];
    let totalVotes = props.data.users.length - 1;

    props.data.users.forEach((user) => {
        if (!user.name.includes("@")) {
            if (user.selectedCard != null) {
                cards.push(user.selectedCard.value);
            } else {
                cards.push("?");
            }
        }
    });

    let uniqueElements = [...new Set(cards)];
    const elementCounts = uniqueElements.map((value) => [
        value,
        cards.filter((str) => str === value).length,
    ]);

    return (
        <div className="align-row-center">
            <div className="total-votes">
                <div className="circle">
                    <h5 className="text-center circle-content-center">
                        {totalVotes} Players
                    </h5>
                    <h5 className="vote-txt text-center">voted</h5>
                </div>
            </div>
            <div className="vote-summary align-column-around">
                {elementCounts.map((vote) => (
                    <div>
                        <h5 className="vote-value">
                            <i className="fa-solid fa-circle vote-dot"></i>
                            {vote[0]}
                        </h5>
                        <h6 className="vote-percentage">
                            {((vote[1] * 100) / totalVotes).toFixed(1)}% ({vote[1]} players)
                        </h6>
                    </div>
                ))}
            </div>
        </div>
    );
};
export default VotingSummary;

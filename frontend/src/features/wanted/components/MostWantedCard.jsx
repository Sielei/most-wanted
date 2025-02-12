import {useNavigate} from "react-router-dom";

const MostWantedCard = ({wantedPerson}) => {
    const isWanted = wantedPerson.caution ? true : false;
    const isSeekingInfo = wantedPerson.caution ? false :
        wantedPerson.subjects.includes("Seeking Information") ? true : false;
    const navigate = useNavigate();
    function inchesToFeetInches(inches) {
        if (!inches) {
            return 'Unknown'
        }
        const feet = Math.floor(inches / 12);
        const remainingInches = inches % 12;
        return `${feet}'${remainingInches}"`;
    }

    function extractRewardFromRewardText(text){
        const regex = /\$[\d,]+/;
        const reward = text.match(regex);
        console.log(reward[0]);
        return reward[0];
    }

    function handleClick(){
        navigate(`/wanted/${wantedPerson.uid}`, {
            state: { wantedPerson: wantedPerson }
        });
    };
    return (
        <div onClick={handleClick}
             className="w-full max-w-sm bg-white border border-gray-200 rounded-lg shadow-sm hover:shadow-md transition-all overflow-hidden cursor-pointer">
            {/* Status Banner */}
            <div className={`text-white text-sm font-semibold px-4 py-1 text-center 
            ${ isWanted ? 'bg-red-600': 'bg-blue-600' }`}>
                {isWanted ? 'WANTED BY THE FBI' : isSeekingInfo ? 'SEEKING INFORMATION' : 'MISSING PERSON'}
            </div>

            {/* Profile Image */}
            <div className="relative">
                <img
                    src={wantedPerson.images[0].original}
                    alt={`${isWanted ? 'Wanted Person' : 'Missing Person'}`}
                    className="w-full aspect-[4/3] object-cover"
                />
                <div className="absolute bottom-0 left-0 right-0 bg-gradient-to-t from-black/70 to-transparent p-4">
                    {wantedPerson.images[0].caption && <span className="text-white text-sm font-medium">
                        {wantedPerson.images[0].caption}
          </span>}
                </div>
            </div>

            {/* Person Information */}
            <div className="p-6 space-y-4">
                <div className="space-y-2">
                    <h2 className="text-2xl font-bold text-gray-900">{wantedPerson.title}</h2>
                    {wantedPerson.warning_message && <p className="text-red-600 font-semibold text-sm">
                        {wantedPerson.warning_message}
                    </p>}
                </div>

                {!isSeekingInfo && <div className="grid grid-cols-2 gap-4 text-sm">
                    <div>
                        <p className="text-gray-500 font-medium">Age</p>
                        <p className="text-gray-900">{
                            wantedPerson.age_max ? wantedPerson.age_max : 'Unknown'
                        }</p>
                    </div>
                    <div>
                        <p className="text-gray-500 font-medium">Height</p>
                        <p className="text-gray-900">{inchesToFeetInches(wantedPerson.height_max)}</p>
                    </div>
                    <div>
                        <p className="text-gray-500 font-medium">Eyes Color</p>
                        <p className="text-gray-900">{
                            wantedPerson.eyes ? wantedPerson.eyes : 'Unknown'
                        }</p>
                    </div>
                    <div>
                        <p className="text-gray-500 font-medium">Hair Color</p>
                        <p className="text-gray-900">{
                            wantedPerson.hair ? wantedPerson.hair : 'Unknown'
                        }</p>
                    </div>
                </div>}

                <div className="pt-2">
                    <p className="text-gray-500 font-medium text-sm mb-1">
                        {isWanted ? 'Wanted For' : isSeekingInfo ? 'Details' :'Last Seen'}
                    </p>
                    <p className="text-gray-900 text-sm leading-snug">
                        {wantedPerson.description}
                    </p>
                </div>

                {wantedPerson.reward_text && (
                    <div className="pt-2">
                        <p className="text-lg font-semibold text-green-700">
                            Reward: {extractRewardFromRewardText(wantedPerson.reward_text)}
                        </p>
                    </div>
                )}

                <div className="pt-2">
                    <p className="text-xs text-gray-500">
                        If you have any information, contact your local FBI office or submit a tip at tips.fbi.gov
                    </p>
                </div>
            </div>
        </div>
    );
};

export default MostWantedCard;
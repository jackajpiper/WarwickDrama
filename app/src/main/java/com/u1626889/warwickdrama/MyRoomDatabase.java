package com.u1626889.warwickdrama;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

@Database(entities = {Post.class}, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract PostDao postDao();

    private static volatile MyRoomDatabase INSTANCE;
    static MyRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "WD_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }

    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PostDao mDao;

        PopulateDbAsync(MyRoomDatabase db) {
            mDao = db.postDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            // TODO - see if there's another way to generate ids for the posts, this way takes up view ids
            Post post = new Post(View.generateViewId(),"Term 2 Sketch Show Auditions", "Paolo Nizam", "Audition", "All", "Dear all, hope you're having a fantastic Sunday before the start of term. A little news for yall, Comedy Society are hosting auditions on Monday 7th and Tuesday 8th for a comedy sketch show in Week 5 of Term 2. So if you're looking for something a little more laid back, experimental or even a chance to test out your comedic feel, then this is the gig for you. Auditions will be held;\n" +
                    "Monday 7th from 2-6pm in H4.22/4\n" +
                    "Tuesday 8th from 4-8pm in H1.07\n" +
                    "SIGN UP now for auditions on Doodle!!\n" +
                    "Any questions or queries, please feel free to DM me or Sam Easton - We're more than happy to help x\n", "Comedy,jokes,sketch,show,experimental","08/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"FreshFest 2019 Auditions", "Becky Wadley" , "Audition", "Freshblood", "Saturday 12th January, 1130-1630, B2.04/05\n" +
                    "Sunday 13th January, 1130-1600, B2.04/05\n" +
                    "\n" +
                    "Auditions for FreshFest 2019 are here! Come and audition on Saturday 12th or Sunday 13th for a chance to get involved in the freshest drama festival at Warwick!\n" +
                    "If you've never auditioned before, this is the perfect opportunity to try it out!\n" +
                    "\n" +
                    "Auditions are run in the style of speed dating - you'll get 5 minutes with each show and will read a short extract with the directors/producers. You'll be there for 30 minutes and will have read for 6 different plays!!!\n" +
                    "By the end of Sunday's auditions, all the teams will sit down and (hopefully) we will find a part that works for you! There's a lot of roles available, so chances are there's something available for you!\n" +
                    "There's no need to prepare anything- all extracts are provided in the room. Feel free to sign up to audition in pairs if you feel nervous auditioning by yourself, just put both of your names on the doodle.\n" +
                    "Sign up on the doodle: https://doodle.com/poll/eycs5qz5nrxm39a7\n" +
                    "\n" +
                    "And best of luck!!!\n" +
                    "\n" +
                    "Any questions please message the page or contact Becky Wadley and Kathryn Llewellyn. \n", "Freshfest,chill,beginner,fresher,speed,dating,doodle,acting", "13/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"ShakeSoc Presents: Hamlet Auditions", "George Chopping" , "Audition", "ShakeSoc", "\"the play's the thing\n" +
                    "Wherein I'll catch the conscience of the king.\"\n" +
                    "\n" +
                    "Hamlet has returned from university in Wittenburg to Elsinore Ltd, a family corporation specialising in life insurance for the funeral of their father (and CEO of Elsinore Ltd). They soon discover their mother Gertrude has hastily remarried with their late father's brother, Claudius, who has inherited ownership of the business. Hamlet is informed by the security watchmen that there have been sightings of a presence resembling that of their father in the building. Hamlet decides they will seek and confront the spirit, unaware that the information they soon discover begins to set the fateful repercussions in motion.\n" +
                    "\n" +
                    "Auditions will be held on the Wednesday, Thursday and Friday of Week 1. Each audition will be 15 minutes. Please have a look at one of the given extracts and be prepared for any re-direction or to explore an alternative extract at the audition.\n" +
                    " \n" +
                    "No need to learn any lines, just have a look over.\n" +
                    "\n" +
                    "We are looking for a cast of 12 to play 22 roles within the show, 6 performers playing single roles and 6 multi-rolling performers.\n" +
                    "Those auditioning for single roles will also be considered for multi - rolling.\n" +
                    "\n" +
                    "The characters of Claudius, Ghost and Laertes,  we are casting Male identifying, whilst Gertrude and Ophelia are Female identifying. The remaining characters including HAMLET are being cast gender blind. Those who identify as non - binary are encouraged to audition for any character.\n" +
                    "\n" +
                    "All roles will be cast race blind.\n" +
                    "\n" +
                    "Claudius will also be playing the role of Ghost, therefore if you want to audition for this role, familiarise yourself with both extracts as you may be asked to go through both.\n" +
                    "\n" +
                    "Please be aware that ‘Hamlet’ contains references to murder, suicide and potential onstage nudity, so please take this into account when auditioning.\n" +
                    "\n" +
                    "AUDITION EXTRACTS: https://docs.google.com/document/d/13M9QS5SbMLyPM7biWpKhGcVEWchvmjIUEIWSwAYhXlI/edit?usp=sharing\n" +
                    "\n" +
                    "SIGN UP FOR AUDITIONS HERE: https://doodle.com/poll/vpnm95x63gmfwmir\n" +
                    "\n" +
                    "ROOMS:\n" +
                    "Wednesday: OC1.07: 19: 00 - 21:00\n" +
                    "Thursday: W.MUSIC 1: 16:15 - 21:00\n" +
                    "Friday: B2.01: 17:15 - 21:00\n" +
                    "\n" +
                    "MULTI-ROLING CHARACTER WORKSHOP RECALL - SATURDAY 12TH JANUARY\n" +
                    "SINGLE ROLE CHARACTER RECALLS - SUNDAY 13TH JANUARY\n" +
                    "\n" +
                    "Performance of Hamlet will take place WEEK 9.\n" +
                    "\n" +
                    "If you have any concerns about this, or any questions at all, please feel free to drop us a message prior or after your audition.\n" +
                    "\n" +
                    "Lauren Baulch (Producer) - L.Baulch@warwick.ac.uk\n" +
                    "Caitlin Tracey (Assistant Producer) - ctracey1323@gmail.com\n" +
                    "\n" +
                    "We look forward to seeing all of you. GOOD LUCK.\n", "Shakespeare,acting,hamlet,drama,performing", "13/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Freshblood Presents: Falling Birds Auditions", "Lizzy Plant" , "Audition", "Freshblood", "Falling Birds is a play about memory. Love. Life. Death. It is the story of Kit, as he confides it to the Moon. The play is episodic and poetic; a lifetime told only through snapshots of a person’s dearest memories. It has no set location, and no set time. It is heart-warming and heart-breaking; a story filled with starlight, sunflowers, and sadness. It reminds us to appreciate those we take for granted. We never know when they may be gone.\n" +
                    "\n" +
                    "WARNING\n" +
                    "Please be aware Falling Birds contains strong language, and mild references to and/or themes of self-harm, mental health, death, suicide and alcohol abuse.\n" +
                    "\n" +
                    "\n" +
                    "We are looking for a cast of six actors to fill our roles. The gender breakdown is as follows:\n" +
                    "\n" +
                    "KIT – male identifying\n" +
                    "SHE – female identifying\n" +
                    "DELILAH - female identifying\n" +
                    "MIRANDA – female identifying\n" +
                    "YOUNG KIT – gender neutral*\n" +
                    "FLORRIE - female identifying\n" +
                    "\n" +
                    "*the role of Young Kit will be cast gender neutral, but played as male\n" +
                    "\n" +
                    "Character biographies and extracts can be found here:\n" +
                    "\n" +
                    "https://drive.google.com/drive/folders/1uNd0s2apYwQvTTOHmaah83_wc21PxN4y?fbclid=IwAR05YhBG_UrxGVlOy8oBOzBodCtnJos1q2PTaF9tUig5feCh5tlBy81DttQ\n" +
                    "\n" +
                    "Sign up for an audition slot below:\n" +
                    "(Room locations TBC)\n" +
                    "\n" +
                    "Wednesday 9th of January - From 13:00 - 19:00\n" +
                    "Room H2.46\n" +
                    "https://doodle.com/poll/vc73b63tpgutk2dg\n" +
                    "\n" +
                    "Thursday 10th of January - From 16:30 - 20:00\n" +
                    "Room H2.46\n" +
                    "https://doodle.com/poll/gfmduqu3nnheqmf5\n" +
                    "\n" +
                    "Friday 11th of January - From 13:00 - 19:00\n" +
                    "Room H5.22\n" +
                    "https://doodle.com/poll/zfxm7yfhwwvkdymp\n" +
                    "\n" +
                    "If you have issues with accessing the doodle or audition extracts please do drop a message to our producers, Paolo Nizam or Sam Easton, or send an email to pnizam98@gmail.com\n" +
                    "\n" +
                    "*\n" +
                    "\n" +
                    "SOME TIPS FOR YOUR AUDITION:\n" +
                    "\n" +
                    "Silences, paragraphing and punctuation are essential to this script. The text sways constantly between long, lyrical speech and quick, contemporary dialogue, so being aware of the register and the various meanings of each sentence are vital to understanding these characters. Play around with meaning and inflection, and use silence to your advantage.\n" +
                    "\n" +
                    "Make sure you arrive with enough time beforehand to ready yourself mentally. We understand auditions can be nerve-wracking and will do everything we can to make you feel comfortable in the audition space, but audition slots are short and we want to see as much of you as possible in that time! Just wait outside the room and we will come and get you when it’s time.\n" +
                    "\n" +
                    "This is the first time these characters will ever be played, so there is no right or wrong way to approach them. We are looking to see how you, personally, may breathe life into them. Take your time and consider how you might connect with each character, and just have fun with it.\n" +
                    "\n" +
                    "In extracts where another character is present, give us YOUR interpretation of the interaction. A member of our team will read in, and it is up to YOU to direct us where you will. We will then have a nice chat about the extract and why you chose your character, and then either redirect or give you another extract to read through. It’s an opportunity: 1) for you to talk through your connections with your chosen character, which is very important to a play so strongly rooted in human connection, and 2) for us to potentially see you tackle more than one character, as they are all so unique and evocative in their own ways.\n" +
                    "\n" +
                    "Owing to the possibility we may ask you to read for more than one character, please be sure to look over all extracts. It is not necessary to memorise your chosen extract, but please be familiar with it so we can make the most of our time together.\n" +
                    "\n" +
                    "We look forward to seeing your take on these never-before-seen characters!\n", "Monologue,art,memory,love,life,death,moon,poetry", "13/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"DC Term 3 Studio Submissions", "Sam Dell" , "Announcement", "All", "Term Three DC Studio show submissions are now OPEN!\n" +
                    "\n" +
                    "This is your chance to put on a show in the Warwick Arts Centre Studio Theatre with the backing of Codpiece (for devising and adaptations), Freshblood New Writing (for original student writing), Tech Crew, and/or WUDS (for published plays).\n" +
                    "\n" +
                    "Whilst the majority of submissions will apply through only one society (with the exception being that Tech Crew do not produce shows on their own), we are also open to (and would love to see!) applications for a co-production between any pair of these societies.\n" +
                    "\n" +
                    "The slots you can submit for this time around are in week 2 and week 8 of term three.\n" +
                    "\n" +
                    "The submission process will run similarly to last term, keeping the pack format re-introduced in our Term 2 submissions. Don't forget that there is plenty of support available and a template you can download if you feel more comfortable sticking to the form setup from previous years.\n" +
                    "\n" +
                    "All the resources you'll need to get started can be found on our Google Drive under 'Drama Collective Resources':\n" +
                    "https://drive.google.com/open?id=1ssVZa2ZVzh2XnsEwMPc1TZXkYAlCNuPz\n" +
                    "\n" +
                    "The key documents you'll need are:\n" +
                    "\n" +
                    "- Online Application of Interest form:\n" +
                    "This online form needs to be filled-in as the first step towards getting your show in the Studio:\n" +
                    "https://goo.gl/forms/1eR5X9hYBgk6d0ly2\n" +
                    "\n" +
                    "- Pack Requirements:\n" +
                    "This PDF document contains everything you'll need to know about the process including how to make your pack and all the deadlines you'll need to add to your diary. It is strongly recommended that you read this document thoroughly before starting your application.\n" +
                    "\n" +
                    "- Budget Template:\n" +
                    "The budget template is back along with some small corrections discovered by last terms' teams.\n" +
                    "\n" +
                    "- Pack Template:\n" +
                    "If you don't know how to begin writing your pack, this document should give you a strong starting point. You can choose to edit it as much as you like or fill it in as it is, it's up to you!\n" +
                    "\n" +
                    "The key deadlines are:\n" +
                    "\n" +
                    "- Saturday 12th January (week 1)\n" +
                    "Deadline for submitting the online Application of Interest form. You are advised to get this done as soon as possible so that you can meet with the Productions Manager of the society(-ies) you are submitting through to discuss your initial application and to give you more time to spend working on your pack.\n" +
                    "\n" +
                    "- Saturday 19th January (week 2)\n" +
                    "Deadline for sending your pack to the DC Productions Manager for feedback. We will aim to have all feedback returned by Friday 25th Jan (week 3) however this will depend on the number of submissions we receive.\n" +
                    "\n" +
                    "- Friday 1st February (week 4)\n" +
                    "Deadline for sending your final pack and a complete script (where appropriate) to the DC Productions Manager.\n" +
                    "\n" +
                    "Please do not hesitate to get in touch with any questions you might have by emailing Sam Dell (DC Productions Manager) at S.Dell@warwick.ac.uk\n", "pack,direct,submission,dc,collective,interest,application", "19/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"WITS Refresher Session 2019", "Charlie Cooper" , "Workshop", "Warwick Improvised Theatre Society", "New year, new improv! Join us on Thursday for a no-strings-attached improv session, for existing members and people wanting to give it a try!\n" +
                    "\n" +
                    "Zero previous experience expected and no membership is required, so new people come along, and existing members bring a friend!\n", "Beginner,friendly,improv,new,try", "10/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Cool opportunity in Brum!", "Greta April" , "Misc", "All", "Hi guys \uD83C\uDFAC\uD83C\uDFAC\n" +
                    "\n" +
                    "I'm looking for an actor aged 18 - 24, for a film we are shooting this Thursday 10th January in Birmingham city centre. Needs to have a bit of experience as we need him to perform a few lines of a poem on camera. The actor needs to be free on 7th February as well, as this will be a second filming date, always in Birmingham.\n" +
                    "\n" +
                    "Can anyone help? It's paid and we will clip up all the footage to use on their showreel.\n" +
                    "\n" +
                    "If interested and available please send an email to francesca@tinkertaylor.tv.\n" +
                    "Thanks all, but mostly Greta \uD83D\uDE02\n", "acting,birmingham,film,short,poem,showreel,paid", "07/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"*SCREEN ACTING OPPORTUNITY*", "Ben Chapman" , "Audition", "All", " Looking for someone to play lead female (playing age 20-21) in a short film for final year project filming on campus and in Leamington on the 19th and 20th of January.\n" +
                    "\n" +
                    "Alicia is newly graduated from University and feels uncertain about her future. She knows what she wants to do but is afraid to pursue it, instead opting to please her working-class father who wants her to find a job but for the sole purpose of obtaining an income. After another failed interview and an argument with her dad, she finds the self confidence to pursue what she actually wants to do with her life.\n" +
                    "Alicia is quite shy in nature and lacks confidence. She is afraid to pursue the career that she wants because she can be quite introverted, and she wants to please her family.\n" +
                    "\n" +
                    "If interested, contact Megan Sutcliffe for more info!\n", "Short,film,audition,casting,call,lead,female", "19/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"*STUDIO SHOW OPPORTUNITY*\n", "Chloe Binfield" , "Crew/production opportunity", "All", "Looking for people interested in marketing and makeup for a week 8 term 3 studio show! Please message me for more info! Thanks ❤️❤️", "Studio,show,opportunity,marketing,makeup", "01/02/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Le Monocle Auditions", "Tiff Milner" , "Audition", "All", "Great Egg Theatre Company are looking for actors in a new production, “le Monocle”, written by Warwick University alumni Tiff Milner. le Monocle will be showing at Edinburgh Fringe on August 2-9. The confirmed venue is theSpace@Surgeons Hall, Theatre 5, 7:15pm. Looking for 6 female identifying actresses and 1 male identifying actor.\n" +
                    "\n" +
                    "le Monocle is a LBGTQA+ centred true-life/historical play, centred around the famous 20th century Parisian lesbian bar of the same name. The story pivots on the relationships and lives of the bar’s patrons and the life of Violette Morris, athlete and race car driver turned German SS agent.\n" +
                    "\n" +
                    "Looking for dedicated actors who are willing to fall in love with a piece of new writing and build a strong, fun and powerful Edinburgh experience. Actors will need to contribute to accommodation in Edinburgh, but will receive this contribution back once box office returns/funding etc. comes back post-Fringe. Actors will be expected to be dedicated to rehearsals, but will be supported and will be part of a big ol’ family!\n" +
                    "\n" +
                    "Plus size actresses/actors strongly encouraged to apply.\n" +
                    "\n" +
                    "Note: You are allowed to audition for more than one character if you like more than one. You’ll be assigned the role you performed best.\n" +
                    "\n" +
                    "Any questions can be directed to Tiff Milner on Facebook, posted here or greateggtheatre@gmail.com\n" +
                    "\n" +
                    "Google Drive: https://drive.google.com/drive/folders/1bhj3slES9Bd4L8-g0wAeLifoxSGml3Ns?usp=sharing\n" +
                    "\n" +
                    "SIGN UPS ARE NOW CLOSED\n" +
                    "\n" +
                    " Audition locations:\n" +
                    "\n" +
                    "Thursday between 9am and 4pm: W.MUSIC2\n" +
                    "Thursday between 4pm and 7pm: W.MUSIC1\n" +
                    "\n" +
                    "Friday between 10am and 12pm: WA1.10\n" +
                    "Friday between 3pm and 6pm: H0.03\n" +
                    "\n" +
                    "Saturday all day: H2.44\n" +
                    "\n" +
                    "Sunday all day: H2.44\n", "lgbt,lesbian,gay,bar,edinburgh,fringe,historical,period,new", "20/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"*STUDIO OPPORTUNITY*", "Dan Stubbs" , "Crew/production opportuntiy", "All", " Term 3 Week 8 show:\n" +
                    "\n" +
                    "Hi gang, so remember when I made a post at the end of last term asking for team members for a show pack I’m involved in? WELL WE STILL NEED PEOPLE! This show is gonna be NUTS and we are looking for a Marketing Manager, Marketing Assistant, Publicity Designer and a Makeup Designer! Message me, Mollie Tucker, Lucy Kitcher or Luke Mott for details on the play and what each role entails \uD83D\uDE0A\n", "studio,show,crew,marketing,manager,publicity,makeup,designer,play", "01/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"WUDS Fund Presents: Grounded - Auditions\n", "Joanna Woznicka" , "Audition", "Warwick University Drama Society", "WUDS Fund Presents: Grounded by George Brant, performed in Week 9.\n" +
                    "\n" +
                    "SUMMARY:\n" +
                    "The Pilot is a workaholic, US F16 fighter pilot in a profession where she is outnumbered by men. When she unexpectedly becomes pregnant, she is faced with having to compromise her career for the first time in her life. On returning to work after maternity leave, she is transferred to the ‘chair-force’ to operate a UAV in the Middle-East, using joystick controls and a computer screen to track and sometimes bomb targets. She is relocated to Las Vegas with her family, allowing her to have the best of both worlds: her family life and the career that she loves. However, as the play progresses, the monotony of working 12 hours a day, 7 days a week, driving to and from home to stare at a computer screen begins to overwhelm her and she becomes unable to separate her home life from her work life.\n" +
                    "\n" +
                    "An online copy of the whole play can be found on the Warwick Library website.\n" +
                    "\n" +
                    "CAST:\n" +
                    "We are looking to cast 5 FEMALE actors to bring this play to life. The roles will consist of 1 actor playing The Pilot (who will have all speaking lines) and a supporting ensemble of 4 actors. All roles will be cast race-blind.\n" +
                    "\n" +
                    "The ensemble will be a very exciting addition to the play and will be used in creative ways to aid its storytelling through movement and staging. We are looking for creative actors who can move and work well in an ensemble so don’t worry if you are not a dancer!\n" +
                    "\n" +
                    "AUDITIONS:\n" +
                    "The audition process will comprise of a group workshop audition AND an individual slot to work on the text. The Pilot will be very much involved with the ensemble and movement, therefore if you would like to be considered for the role of The Pilot, you must attend BOTH a workshop audition AND an individual slot. If you would like to only be considered for the ensemble, you would only be required to attend a workshop audition.\n" +
                    "\n" +
                    "For the individual slots, please prepare an extract from the play which is linked below. Don’t feel you have to learn this, just have a bit of a read through and come with some ideas. The Pilot is an American character, so if you are comfortable with trying out your American accent then please do but this is not a requirement. Please be prepared for redirection or to look at a fresh extract. Each slot will last 15 minutes.\n" +
                    "\n" +
                    "The workshop audition will last for 45 minutes and will have a maximum of 15 people in each slot. Each workshop will consist of a few ensemble exercises and a bit of movement, no need to prepare anything, just come wearing clothing that you would be comfortable moving in i.e. joggers, leggings and t-shirts. This will be a really chilled workshop and a great opportunity to have some fun and try out some theatre.\n" +
                    "\n" +
                    "AUDITION EXTRACT FOR THE PILOT:\n" +
                    "https://docs.google.com/document/d/1Gc-yu-PXubiqoXw8D-02Z2V3GLW90PB_i4J251y5Q7I/edit?fbclid=IwAR2txAK3XDqOu72fK3vF2QOtREh6dfmt4IlI0Xohot3E8gVRwe_fUrIdqiw\n" +
                    "\n" +
                    "…….\n" +
                    "\n" +
                    "SIGN UP FOR AUDITIONS HERE:\n" +
                    "Solo Auditions: https://doodle.com/poll/p4uzv37bty7fgvk8\n" +
                    "Workshop: https://doodle.com/poll/c8xuvew722v2x53c\n" +
                    "\n" +
                    "AUDITION LOCATIONS:\n" +
                    "Thursday: WA1.09 (in the Avon Building).\n" +
                    "Friday: 2.00pm – 3.00pm: H3.56 / 3.00pm – 5.30pm: H4.45\n" +
                    "Saturday: 10.00am – 1.00pm: WA0.15a (in the Avon Building)/ 6.00pm – 9.00pm: H2.46\n" +
                    "\n" +
                    "RECALLS: Sunday: 1.00pm – 4.00pm: WA1.09 (in the Avon Building)\n" +
                    "\n" +
                    "Any questions, feel free to ask on this page or message the producer, Joanna Woznicka (email: Joanna.Woznicka@warwick.ac.uk).\n" +
                    "\n" +
                    "This amateur production of “Grounded” is presented by special arrangement with SAMUEL FRENCH LTD.\n", "Audition,female,lead,workshop,pilot,perform", "20/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"WUDS Minority Fund Auditions: Silence! The Court is in Session\n", "Sumina Kasuji" , "Audition", "Warwick University Drama Society", " “The morality which you have shown through your conduct was the morality that you were planning to impart on the youth of tomorrow”\n" +
                    "\n" +
                    "WUDS Minority Fund Presents: Silence! The Court is in Session by Vijay Tendulkar. A black-comedy written in 1960s India, the play centres around a village amateur dramatics group who are meeting to rehearse a mock trial to demonstrate court proceedings to a young man, Samant.\n" +
                    "\n" +
                    "We are looking for a cast of 8 actors for our production.\n" +
                    "\n" +
                    "The character of BENARE will be cast RACE-SPECIFIC for themes which are present in the text. Only actors from ethnic minority backgrounds will be considered for the role of Benare.\n" +
                    "\n" +
                    "All other roles will be cast non-race specific, however we strongly encourage actors from MINORITY backgrounds to audition.\n" +
                    "\n" +
                    "Due to the gender issues in the play, we will be casting this play GENDER SPECIFIC, WITH THE EXCEPTION OF ONE CHARACTER. Non-binary actors are free to audition for any role they feel comfortable with.\n" +
                    "\n" +
                    "In particular we are looking for:\n" +
                    "BENARE (FEMALE IDENTIFYING)\n" +
                    "MRS KASHIKAR (FEMALE IDENTIFYING)\n" +
                    "\n" +
                    "KARNIK (NON-SPECIFIC)\n" +
                    "\n" +
                    "MR KASHIKAR (MALE IDENTIFYING)\n" +
                    "SUKHATME (MALE IDENTIFYING)\n" +
                    "SAMANT (MALE IDENTIFYING)\n" +
                    "PONKSHE (MALE IDENTIFYING)\n" +
                    "ROKDE (MALE IDENTIFYING)\n" +
                    "\n" +
                    "We cannot stress enough that we want to see students from all backgrounds to audition, regardless of experience. EVERYONE is welcome and we promise auditions will be friendly and welcoming. We want you to enjoy the experience as much as possible.\n" +
                    "\n" +
                    "Please be aware that this play contains themes of depression, self-harm and misogyny.\n" +
                    "\n" +
                    "\n" +
                    "AUDITION:\n" +
                    "\n" +
                    "Auditions will be in the format of an ensemble workshop since all characters are present on stage for the majority of the production.\n" +
                    "\n" +
                    "During the workshop you will also have the opportunity to read for a particular role with a member of our team.\n" +
                    "\n" +
                    "Audition extracts will be posted shortly, and brief character descriptions can be found below:\n" +
                    "\n" +
                    "https://drive.google.com/drive/folders/1LTZB0iYJ44I5yx2ZJDcLdkFoWABT_PxX?fbclid=IwAR2KWZBgKrRWO2YExmmwE1KhOqToxVNYRfhziycoVK9c9UlJ29vJoxFyKiQ\n" +
                    "\n" +
                    "In the workshop, the directors will look to see how you work in a group, as well as individually. The extracts for the group aspect of the audition will be provided on the day as we would like to see how you engage with the text upon first sight.\n" +
                    "\n" +
                    "Audition workshops will take place on Wednesday 16th January at 1-3pm (W.MUSIC3) and 5-7pm (B2.01 Sci Conc), The doodle for signups is below:\n" +
                    "\n" +
                    "https://doodle.com/poll/yxt94t6bfcg8hw5d\n" +
                    "\n" +
                    "Recalls will then take place on Thursday 17th January 2019, with rooms and timings to be confirmed. If you'd like to audition but can't make the times above please send us a message and we'll be happy to help!\n" +
                    "\n" +
                    "If you have any questions, or queries regarding the play and the audition process, please do not hesitate to contact either Aaron Chote (Co-director) aaronchote@gmail.com, Sumina Kasuji (Co – director) s.kasuji@warwick.ac.uk, Fatima Ali-Omar (Producer) f.omar@warwick.ac.uk, or message the FB page.\n" +
                    "\n" +
                    "We look forward to seeing you!\n" +
                    "\n" +
                    "\n" +
                    "PLAY AND CHARACTER SUMMARY:\n" +
                    "\n" +
                    "Leela BENARE is the protagonist of this piece. A free-spirited school teacher with a sharp mind, and cheeky sense of humour. She knows how to stand her ground and is aware of how the other characters see her as a stubborn, rebel.\n" +
                    "\n" +
                    "When Professor Damle fails to turn up for the rehearsal, Benare is nominated by the other actors to play the accused in their mock trial – under the charge of infanticide. Benare reluctantly accepts her new part, but as the egos and outrageous performances build up, the free-flowing rehearsal soon turns into an almost personal attack on Benare herself. Benare is accused of adultery with the Professor, the absent actor in the rehearsal, and carrying his baby. But is there more truth to this than meets the eye? Is this just a fictional story made up for the show?\n" +
                    "\n" +
                    "MRS KASHIKAR is the long-suffering wife of Mr Kashikar, her first name is never mentioned in the play and always seems to be being shouted down by her obnoxious husband.\n" +
                    "\n" +
                    "MR KASHIKAR plays the role of the judge in the mock trial but spends most of his time picking his ears and berating his wife.\n" +
                    "\n" +
                    "ROKDE works for the Kashikars and is usually being wound up by a bored Benare or ridiculed by his employers.\n" +
                    "\n" +
                    "SUKHATME is an actual lawyer and uses the rehearsal as an opportunity to show off and inflate his ego.\n" +
                    "\n" +
                    "KARNIK is an actor, who specialises in experimental theatre, and plays a key witness.\n" +
                    "\n" +
                    "PONKSHE is a clerk who also plays a key witness, and it is revealed that he and Rokde both know something about Benare that she wants to be kept secret.\n" +
                    "\n" +
                    "SAMANT is a sweet, well-meaning young man and also the one for whom the mock trial is being staged for. At times, he is too innocent for his own good; unwavering in his kindness, Samant is probably the only character in the play without any prejudice toward anyone. He has a fondness for Benare and tries his best to help in his first rehearsal.\n", "minority,fund,india,race,morality,welcoming,friendly,depression,misogyny", "17/01/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Production Team Database 2018-19 Sign-up", "Sam Dell" , "Crew/production opportunity", "All", "With a whole bunch of shows happening this Term, there are plenty of teams looking to fill roles on their production team. If you’re interested in being contacted about vacancies in certain roles then make sure you’ve added yourself to our Production Team database:\n" +
                    "\n" +
                    "https://docs.google.com/…/1j6Dgn-7KVeFd0gVeYmIsyGYREUpUFb8o…\n", "production,opportunity,sign,up,database,marketing,photography,vacancies,makeup,lighting,sound,design", "16/03/2019");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Falling Birds Shadow Directing", "Paolo Nizam" , "Crew/production opportunity", "Freshblood", "**NEWS** (SHADOW DIRECTING OPPORTUNITY) ** READ TILL END** DEADLINE FRIDAY 25TH - 12:00PM\n" +
                    "\n" +
                    "Hello all <3 Hope you've all had a fantastic week!! Some absolutely brilliant news for those who didn't make it through recalls/auditions x\n" +
                    "\n" +
                    "Falling Birds is now opening up a place to Shadow-Direct alongside our brilliantly talented Director Charlotte and Writer Lizzy. This will mean you have the opportunity to become one of the team!! Don't miss out on an opportunity to gain invaluable experience being part of a fantastic team :P\n" +
                    "Below is a link to a google doc with further information in regards to how to apply/what we're looking for.\n" +
                    "\n" +
                    "https://docs.google.com/…/1ZQNIajEYlm2oP5hApONSmM0KlH…/edit…\n" +
                    "\n" +
                    "If you wish to apply, bear in mind the DEADLINE is Friday the 25th at 12:00pm Week 3 of term.\n" +
                    "\n" +
                    "The email in which to send the document off to is pnizam98@gmail.com. If you have any further questions, please feel free to get in contact with our Producers Paolo Nizam/Sam Easton <3 <3\n" +
                    "\n" +
                    "Lots of Love - Falling Birds Team x\n", "Shadow,directing,opportunity,fun,experience,apply", "25/01/2019");
            mDao.insert(post);


            return null;
        }
    }


}

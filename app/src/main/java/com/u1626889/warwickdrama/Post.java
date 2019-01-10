package com.u1626889.warwickdrama;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "post_table")
public class Post implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    @NonNull
    @ColumnInfo(name = "owner")
    private String owner;
    @NonNull
    @ColumnInfo(name = "type")
    private String type;
    @NonNull
    @ColumnInfo(name = "society")
    private String society;
    @NonNull
    @ColumnInfo(name = "desc")
    private String desc;
    @NonNull
    @ColumnInfo(name = "tags")
    private String tags;
    @NonNull
    @ColumnInfo(name = "exp_date")
    private String exp_date;

    public Post(int id, String title, String owner, String type, String society, String desc, String tags, String exp_date) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.type = type;
        this.society = society;
        this.desc = desc;
        this.tags = tags;
        this.exp_date = exp_date;
    }

    public int getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getOwner(){return this.owner;}
    public String getType(){return this.type;}
    public String getSociety(){return this.society;}
    public String getDesc(){return this.desc;}
    public String getTags(){return this.tags;}
    public String getExp_date(){return this.exp_date;}


    // Implementing the parcelable methods so we can pass it in an intent
    // We need to pass a list of the posts from PostListAdapter to ViewPostActivity

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(owner);
        out.writeString(type);
        out.writeString(society);
        out.writeString(desc);
        out.writeString(tags);
        out.writeString(exp_date);
    }

    public static final Parcelable.Creator<Post> CREATOR
            = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    private Post(Parcel in) {
        id = in.readInt();
        title = in.readString();
        owner = in.readString();
        type = in.readString();
        society = in.readString();
        desc = in.readString();
        tags = in.readString();
        exp_date = in.readString();
    }

}

package comicreader.software.nhut.doc_truyen.DO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import comicreader.software.nhut.doc_truyen.util.MyPhone;

/**
 * Created by Nhut on 7/2/2017.
 */

public class Truyen implements Serializable, Comparable {
    private String name;
    private String thumbnailLink;
    private String description;
    private List<String> linkList;
    private List<String> tenChapterList;
    private List<String> link;
    public static final String THUMBNAILFILE = "thumbnail.thumbnail";
    public static final String TRUYENINFO = "truyen.info";

    public Truyen() {
        this.name = "";
        this.thumbnailLink = "";
        this.description = "";
        this.link = new ArrayList<>();
        this.linkList = new ArrayList<>();
        this.tenChapterList = new ArrayList<>();
    }

    public Truyen(String name, String thumbnailLink,
                  String description, List<String> linkChapterList) {
        this();
        this.name = name;
        this.thumbnailLink = thumbnailLink;
        this.description = description;
        this.linkList.addAll(linkChapterList);
    }

    public Truyen(String name, String thumbnailLink, String description, String link) {
        this();
        this.name = name;
        this.thumbnailLink = thumbnailLink;
        this.description = description;
        this.link.add(link);
    }

    public List<String> getTenChapterList() {
        return tenChapterList;
    }

    public void setTenChapterList(List<String> tenChapterList) {
        this.tenChapterList = tenChapterList;
    }

    public List<String> getLink() {
        return link;
    }

    public void setLink(List<String> link) {
        this.link = link;
    }

    public List<String> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<String> linkList) {
        this.linkList = linkList;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static List<String> getTruyenInfo(final String chapterLink) {
        final List<String> listLink = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(chapterLink)
                            .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc != null) {
                        Elements elems = doc.getElementsByAttributeValue("class", "");
                        if (elems != null)
                            for (Element e : elems) {
                                String src = e.attr("href");
                                if (src != null) listLink.add(src);
                            }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return listLink;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((Truyen)o).getName());
    }

    public Truyen reverselinkList() {
        List<String> list = new ArrayList<>();
        if (linkList != null && linkList.size() > 0)
            for (int i = linkList.size() - 1; i > -1; i--)
                list.add(linkList.get(i));
        this.linkList.clear();
        this.linkList.addAll(list);
        list.clear();
        if (tenChapterList != null && tenChapterList.size() > 0)
            for (int i = tenChapterList.size() - 1; i > -1; i--)
                list.add(tenChapterList.get(i));
        this.tenChapterList.clear();
        this.tenChapterList.addAll(list);
        return this;
    }

    @Override
    public String toString() {
        return "Truyen{" +
                "name='" + name + '\'' +
                ", thumbnailLink='" + thumbnailLink + '\'' +
                ", description='" + description + '\'' +
                ", linkList=" + linkList.toString() +
                ", link='" + link + '\'' +
                '}';
    }
}
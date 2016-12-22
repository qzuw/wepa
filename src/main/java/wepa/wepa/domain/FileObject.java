/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.wepa.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class FileObject extends AbstractPersistable<Long> {

    private String name;
    private String contentType;
    private Long contentLength;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String mediaType) {
        this.contentType = mediaType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long size) {
        this.contentLength = size;
    }

}

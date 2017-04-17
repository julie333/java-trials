library(stringr)
library(dplyr)
library(data.table)
library(sqldf)
library(ggplot2)
library(grid)
library(gridExtra)
library(reshape2)
library(tcltk)


#Thema Table

Thema <- data.frame(
  themaTitle= Thema_Website$Title,
  link= unlist(lapply(strsplit(Thema_Website$Link.Blog.EN, "/"),tail,1))
)

#Thema Link Table

ThematinyURILink <- data.frame(
  themaTitle= Thema_tinyURL$Title,
  tinyURILink= str_trim(substring(Thema_tinyURL$tinyURI,regexpr("//",Thema_tinyURL$tinyURI)+2,nchar(Thema_tinyURL$tinyURI)))
)

#SocialMediaOutlet_1 Table

SocialMediaOutlet_1 <- data.frame(
  tinyURILink = ifelse(regexpr("//",SocialMediaOutlet_1_data$message)>0,
                     gsub(" .*$","",substring(SocialMediaOutlet_1_data$message,regexpr("//",SocialMediaOutlet_1_data$message)+2,nchar(SocialMediaOutlet_1_data$message))),
                     str_trim(substring(SocialMediaOutlet_1_data$link,regexpr("//",SocialMediaOutlet_1_data$link)+2,nchar(SocialMediaOutlet_1_data$link)))),
  fCommitment= SocialMediaOutlet_1_data$shares_count + SocialMediaOutlet_1_data$comments_count ,
  fAcceptance= SocialMediaOutlet_1_data$Acceptance_count,
  source="SocialMediaOutlet_1"
)

#SocialMediaOutlet_2 Table 

SocialMediaOutlet_2 <- data.frame(
  noticeLink= gsub(" .*$","",substring(c(SocialMediaOutlet_2_data$text),regexpr("//",SocialMediaOutlet_2_data$text)+2,nchar(SocialMediaOutlet_2_data$text))),
  tCommitment= SocialMediaOutlet_2_data$reblitzCount,
  tAcceptance= SocialMediaOutlet_2_data$starCount,
  source= "SocialMediaOutlet_2"
)

#SocialMediaOutlet_3 Table

SocialMediaOutlet_3 <- data.frame(
  noticeLink= blitz_notices$fullReferrer,
  link= unlist(lapply(strsplit(blitz_notices$landingPagePath, "/"),tail,1))
)

#Merged Thema, SocialMediaOutlet_1, SocialMediaOutlet_2, SocialMediaOutlet_3 Table

mThema <-merge(Thema, ThematinyURILink, by=c("themaTitle"))

mSocialMediaOutlet_1 <- merge(mThema, SocialMediaOutlet_1, by=c("tinyURILink"))

mSocialMediaOutlet_3 <- merge(mThema, SocialMediaOutlet_3, by=c("link"))

mSocialMediaOutlet_2 <- merge(mSocialMediaOutlet_3, SocialMediaOutlet_2, by=c("noticeLink"))


#Group SocialMediaOutlet_1 & SocialMediaOutlet_2 by themaTitle

gSocialMediaOutlet_1<- sqldf(" 
                    select   themaTitle,
                             source,
                             sum(fAcceptance) as TotalFAcceptance,
                             sum(fCommitment) as TotalFCommitment
                    from     mSocialMediaOutlet_1
                    group by themaTitle"
)


gSocialMediaOutlet_2<- sqldf("
                   select   themaTitle,
                            source,
                            sum(tAcceptance) as TotalTAcceptance,
                            sum(tCommitment) as TotalTCommitment
                   from     mSocialMediaOutlet_2
                   group by themaTitle"
)



#Plots - Solution 1 

gSocialMediaOutlet_1.long <- melt(gSocialMediaOutlet_1)
gSocialMediaOutlet_2.long <- melt(gSocialMediaOutlet_2)

gSocialMediaOutlet_1.long$themaTitle <- str_wrap(gSocialMediaOutlet_1.long$themaTitle,width = 10)
gSocialMediaOutlet_2.long$themaTitle <- str_wrap(gSocialMediaOutlet_2.long$themaTitle,width = 20)

SocialMediaOutlet_1Plot <-
              ggplot(gSocialMediaOutlet_1.long, aes(x=reorder(themaTitle,-value),value,fill=variable)) + 
              guides(fill=FALSE) +
              geom_bar(stat = "identity", width = 0.5, position="dodge") + 
              scale_fill_manual(values=c("steelblue","indianred")) +
              ggtitle("SocialMediaOutlet_1 - Acceptance & Commitment pro Thema") +
              xlab("Thema Title") +
              ylab("Acceptance & Commitment") +
              ylim(0,max(gSocialMediaOutlet_1.long$value)) + 
              theme(title= element_text(face = "bold.italic",size = 10), axis.title =element_text(face = "bold.italic", size= 10))


SocialMediaOutlet_2Plot <- 
              ggplot(gSocialMediaOutlet_2.long, aes(x=reorder(themaTitle,-value),value,fill=variable)) +  
              guides(fill=FALSE) +
              geom_bar(stat = "identity", width = 0.5, position="dodge") + 
              scale_fill_manual(values=c("steelblue","indianred")) +
              ggtitle("SocialMediaOutlet_2 - Acceptance & Commitment pro Thema") +
              xlab("Thema Title") +
              ylab("Acceptance & Commitment") +
              ylim(0,max(gSocialMediaOutlet_2.long$value)) + 
              theme(title= element_text(face = "bold.italic",size = 10), axis.title =  element_text(face = "bold.italic", size= 10))


#Barchart - Solution 1 - Thema sicht: Acceptance & Commitment pro Thema pro Quelle

SocialMediaOutlet_1_SocialMediaOutlet_2Plot1 <- grid.arrange(SocialMediaOutlet_1Plot, SocialMediaOutlet_2Plot,
                                      grid.legend(c("Acceptance","Commitment"),pch = 22, nrow = 1,draw=TRUE,
                                      gp=gpar(col = c("steelblue","indianred"), fill = c("steelblue","indianred"), fontsize=10)),
                                      ncol=1,nrow=3,heights=c(0.52,0.45,0.03))


#Plots - Solution 2

mSocialMediaOutlet_1_SocialMediaOutlet_2 <- merge(gSocialMediaOutlet_1, gSocialMediaOutlet_2, by = c("themaTitle","source"), all = T)

mSocialMediaOutlet_1_SocialMediaOutlet_2 <- mSocialMediaOutlet_1_SocialMediaOutlet_2 %>%
                     mutate(TotalFAcceptance = ifelse(is.na(TotalFAcceptance),0,TotalFAcceptance),
                            TotalTAcceptance = ifelse(is.na(TotalTAcceptance),0,TotalTAcceptance),
                            TotalFCommitment = ifelse(is.na(TotalFCommitment),0,TotalFCommitment),
                            TotalTCommitment = ifelse(is.na(TotalTCommitment),0,TotalTCommitment)
                            )

gSocialMediaOutlet_1_SocialMediaOutlet_2 <- sqldf(" 
                             select    themaTitle,
                                       sum(TotalFCommitment) + sum(TotalTCommitment) as [Total Commitment],
                                       sum(TotalFAcceptance) + sum(TotalTAcceptance) as [Total Acceptance],
                                       sum(TotalFAcceptance) + sum(TotalTAcceptance) + sum(TotalFCommitment) + sum(TotalTCommitment) as [Total Acceptance & Commitment]
                             from      mSocialMediaOutlet_1_SocialMediaOutlet_2
                             group by  themaTitle
                             order by  sum(TotalFAcceptance) + sum(TotalTAcceptance) + sum(TotalFCommitment) + sum(TotalTCommitment) desc"
)

gSocialMediaOutlet_1_SocialMediaOutlet_2.long <- melt(gSocialMediaOutlet_1_SocialMediaOutlet_2)

gSocialMediaOutlet_1_SocialMediaOutlet_2.long$themaTitle <- str_wrap(gSocialMediaOutlet_1_SocialMediaOutlet_2.long$themaTitle,width = 40)


#Barchart - Solution 2 - Thema sicht: Popularity pro Thema based on Total Acceptance and Total Commitments from SocialMediaOutlet_1 & SocialMediaOutlet_2

SocialMediaOutlet_1_SocialMediaOutlet_2Plot2 <-
              ggplot(gSocialMediaOutlet_1_SocialMediaOutlet_2.long, aes(x=reorder(themaTitle,value),value,fill=variable)) + 
              geom_bar(stat = "identity", width = 0.75, position="dodge") +
              xlab(" ") +
              ylab(" ") +
              guides(fill = guide_legend(reverse=TRUE,title = NULL)) + 
              scale_fill_manual(values=c("indianred","steelblue","darkgray"))+
              ylim(0,max(gSocialMediaOutlet_1_SocialMediaOutlet_2.long$value)) +
              coord_flip() +  
              ggtitle("Popularity pro Thema based on Acceptance and Commitments from SocialMediaOutlet_1 & SocialMediaOutlet_2\n") + 
              theme(title= element_text(face = "bold",size = 12,vjust = 0.5)) 
      
SocialMediaOutlet_1_SocialMediaOutlet_2Plot2


#Plots - Solution 3

gStacked_SocialMediaOutlet_1_SocialMediaOutlet_2 <- sqldf(" 
                                     select    themaTitle,
                                               sum(TotalTCommitment) as [  SocialMediaOutlet_2 Commitment  ],
                                               sum(TotalFCommitment) as [  SocialMediaOutlet_1 Commitment  ],
                                               sum(TotalTAcceptance) as [  SocialMediaOutlet_2 Acceptance  ],
                                               sum(TotalFAcceptance) as [  SocialMediaOutlet_1 Acceptance  ]
                                     from      mSocialMediaOutlet_1_SocialMediaOutlet_2
                                     group by  themaTitle
                                     order by  themaTitle desc"
)

gStacked_SocialMediaOutlet_1_SocialMediaOutlet_2.long <- melt(gStacked_SocialMediaOutlet_1_SocialMediaOutlet_2)

gStacked_SocialMediaOutlet_1_SocialMediaOutlet_2.long$themaTitle = str_wrap(gStacked_SocialMediaOutlet_1_SocialMediaOutlet_2.long$themaTitle,width = 40)

#Barchart - Solution 3 - Thema sicht: Acceptance and Commitments from SocialMediaOutlet_1 & SocialMediaOutlet_2 pro Thema

SocialMediaOutlet_1_SocialMediaOutlet_2_Plot3 <- 
              ggplot(gStacked_SocialMediaOutlet_1_SocialMediaOutlet_2.long,
              aes(reorder(x=themaTitle,-value),value,fill=variable)) + 
              geom_bar(stat = "identity", width = 0.75,position = position_stack()) +
              scale_fill_brewer(palette = "Purples")+
              ggtitle("Acceptance and Commitments from SocialMediaOutlet_1 & SocialMediaOutlet_2 pro Thema\n") +
              xlab(" ") + 
              ylab(" ") +
              guides(fill = guide_legend(title = NULL)) +
              theme(legend.position="bottom",title= element_text(face = "bold",size = 12,vjust = 0.5)) +
              coord_flip() +
              guides(fill = guide_legend(title = NULL,label.position = "bottom")) 

SocialMediaOutlet_1_SocialMediaOutlet_2_Plot3













